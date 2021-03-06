/*
 * MyTake.org website and tooling.
 * Copyright (C) 2017-2018 MyTake.org, Inc.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * You can contact us at team@mytake.org
 */
package auth;

import static auth.AuthModule.ACCEPT_TERMS;
import static auth.AuthModule.CREATE_EMAIL;
import static auth.AuthModule.CREATE_USERNAME;
import static auth.AuthModule.REDIRECT;
import static db.Tables.ACCOUNT;
import static db.Tables.CONFIRMACCOUNTLINK;

import com.google.common.collect.ImmutableSet;
import common.EmailSender;
import common.Emails;
import common.IpGetter;
import common.Mods;
import common.RandomString;
import common.Text;
import common.Time;
import common.UrlEncodedPath;
import db.VarChars;
import db.tables.pojos.Account;
import db.tables.records.AccountRecord;
import db.tables.records.ConfirmaccountlinkRecord;
import forms.meta.MetaField;
import forms.meta.MetaFormDef;
import forms.meta.MetaFormValidation;
import forms.meta.Validator;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;
import java2ts.Routes;
import org.jooby.Request;
import org.jooby.Response;
import org.jooq.DSLContext;

public class CreateAccountForm extends MetaFormDef.HandleValid {
	/** Email links are good for this long. */
	public static final int CONFIRM_WITHIN_MINUTES = 10;

	/** Constraints on the username, https://gist.github.com/tonybruess/9405134 */
	private static final int FACEBOOK_MIN_USERNAME = 5;
	private static final int FACEBOOK_MAX_USERNAME = 50;
	private static final Pattern LOWERCASE_AND_DASH = Pattern.compile("[a-z\\d-]*");

	private static final String msg_ALLOWED_CHARACTERS = "Can only use lowercase letters, numbers, and '-'";
	private static final String msg_USERNAME_NOT_AVAILABLE = "Username is not available";

	@Override
	public Set<MetaField<?>> fields() {
		return ImmutableSet.of(ACCEPT_TERMS, CREATE_USERNAME, CREATE_EMAIL, REDIRECT);
	}

	@Override
	protected void validate(MetaFormValidation validation) {
		// keep all fields except ACCEPT_TERMS
		validation.keep(CREATE_USERNAME, CREATE_EMAIL, REDIRECT);

		if (!validation.parsed(ACCEPT_TERMS)) {
			validation.errorForField(ACCEPT_TERMS, "Must accept the terms to create an account");
		}

		validateUsername(validation, CREATE_USERNAME);

		Validator.strLength(0, VarChars.EMAIL).validate(validation, CREATE_EMAIL);
		Validator.email().validate(validation, CREATE_EMAIL);
	}

	static void validateUsername(MetaFormValidation validation, MetaField<String> field) {
		Validator.strLength(FACEBOOK_MIN_USERNAME, FACEBOOK_MAX_USERNAME).validate(validation, field);
		Validator.regexMustMatch(LOWERCASE_AND_DASH, msg_ALLOWED_CHARACTERS).validate(validation, field);
	}

	static void validateUsernameEmail(String username, String email, DSLContext dsl, MetaFormValidation validation) {
		Integer existingAccountId = dsl.selectFrom(ACCOUNT)
				.where(ACCOUNT.USERNAME.eq(username))
				.fetchOne(ACCOUNT.ID);
		if (existingAccountId != null) {
			validation.errorForField(CREATE_USERNAME, msg_USERNAME_NOT_AVAILABLE);
		}

		Integer accountId = dsl.selectFrom(ACCOUNT)
				.where(ACCOUNT.EMAIL.eq(email))
				.fetchOne(ACCOUNT.ID);
		if (accountId != null) {
			validation.errorForField(CREATE_EMAIL, msg_EMAIL_ALREADY_USED);
		}
	}

	private static final String msg_EMAIL_ALREADY_USED = "This email is already used by another account";

	@Override
	public boolean handleSuccessful(MetaFormValidation validation, Request req, Response rsp) throws Throwable {
		String username = Text.lowercase(validation.parsed(CREATE_USERNAME));
		String email = Text.lowercase(validation.parsed(CREATE_EMAIL));
		if (ReservedUsernames.isReserved(username)) {
			validation.errorForField(msg_USERNAME_NOT_AVAILABLE);
		} else {
			try (DSLContext dsl = req.require(DSLContext.class)) {
				validateUsernameEmail(username, email, dsl, validation);
				if (validation.noErrors()) {
					String code = RandomString.get(req.require(Random.class), VarChars.CODE);

					// store it in the database
					ConfirmaccountlinkRecord confirm = dsl.newRecord(CONFIRMACCOUNTLINK);
					confirm.setCode(code);
					Time time = req.require(Time.class);
					confirm.setCreatedAt(time.nowTimestamp());
					confirm.setExpiresAt(time.nowTimestamp().plus(CONFIRM_WITHIN_MINUTES, TimeUnit.MINUTES));
					confirm.setRequestorIp(req.require(IpGetter.class).ip(req));
					confirm.setUsername(username);
					confirm.setEmail(email);
					confirm.insert();

					// send a confirmation email
					UrlEncodedPath path = EmailConfirmationForm.generateLink(req, validation, AuthModule.URL_confirm_account + code);
					path.param(CREATE_USERNAME, username);
					path.param(CREATE_EMAIL, email);

					req.require(EmailSender.class).send(htmlEmail -> htmlEmail
							.setHtmlMsg(views.Auth.createAccountEmail.template(username, path.build()).renderToString())
							.setSubject("MyTake.org account confirmation")
							.addTo(email));

					rsp.send(views.Auth.createAccountEmailSent.template(email, Emails.TEAM));
					return true;
				}
			}
		}
		return false;
	}

	private static String getNullToEmpty(Request req, MetaField<String> field) throws UnsupportedEncodingException {
		return URLDecoder.decode(req.param(field.name()).value(""), StandardCharsets.UTF_8.name());
	}

	public static void confirm(String code, Request req, Response rsp) throws Throwable {
		Optional<AuthUser> userOpt = AuthUser.authOpt(req);
		// might be a race condition here, depending on issue #97
		//    - fetch code
		//    - create account
		//    - delete all confirmaccounts for that username and email
		// all of the above needs to be a transaction to ensure that the
		// account insertion won't hit uniqueness constraints on username
		// and email
		Time time = req.require(Time.class);
		String ip = req.require(IpGetter.class).ip(req);
		try (DSLContext dsl = req.require(DSLContext.class)) {
			ConfirmaccountlinkRecord link = dsl.selectFrom(CONFIRMACCOUNTLINK)
					.where(CONFIRMACCOUNTLINK.CODE.eq(code))
					.fetchOne();
			MetaFormValidation createAccountValidation = EmailConfirmationForm.nullIfValid(CreateAccountForm.class, req, link,
					ConfirmaccountlinkRecord::getExpiresAt, ConfirmaccountlinkRecord::getRequestorIp);
			if (createAccountValidation != null) {
				String username = getNullToEmpty(req, CREATE_USERNAME);
				if (userOpt.isPresent() && userOpt.get().username().equals(username)) {
					rsp.send(views.Auth.alreadyConfirmed.template(username));
				} else {
					if (userOpt.isPresent() && !userOpt.get().username().equals(username)) {
						// clear the user's existing cookies if they're clicking a "confirm" link for someone else
						AuthUser.clearCookies(rsp);
					}
					String email = getNullToEmpty(req, CREATE_EMAIL);
					validateUsernameEmail(username, email, dsl, createAccountValidation);
					boolean emailAlreadyUsed = msg_EMAIL_ALREADY_USED.equals(
							createAccountValidation.errorForField(AuthModule.CREATE_EMAIL.name()));
					if (emailAlreadyUsed) {
						// since the email is already used, they probably just need to login
						UrlEncodedPath path = UrlEncodedPath.path(Routes.LOGIN)
								.param(AuthModule.LOGIN_EMAIL, email)
								.param(AuthModule.LOGIN_REASON, "Your account is already confirmed, you can login.")
								.paramIfPresent(AuthModule.LOGIN_REASON, req)
								.paramIfPresent(AuthModule.REDIRECT, req);
						rsp.redirect(path.build());
					} else {
						// we'll let them try to create their account again
						rsp.send(views.Auth.createAccountUnknown.template(createAccountValidation.markup(Routes.LOGIN)));
					}
				}
			} else {
				AccountRecord account = dsl.newRecord(ACCOUNT);
				account.setUsername(link.getUsername());
				account.setEmail(link.getEmail());
				account.setCreatedAt(time.nowTimestamp());
				account.setCreatedIp(ip);
				account.setUpdatedAt(time.nowTimestamp());
				account.setUpdatedIp(ip);
				account.setLastSeenAt(time.nowTimestamp());
				account.setLastSeenIp(ip);
				account.setLastEmailedAt(time.nowTimestamp());
				account.insert();

				// delete all other requests from that email and username
				dsl.deleteFrom(CONFIRMACCOUNTLINK)
						.where(CONFIRMACCOUNTLINK.EMAIL.eq(link.getEmail()))
						.or(CONFIRMACCOUNTLINK.USERNAME.eq(link.getUsername()))
						.execute();

				AuthUser.login(account.into(Account.class), req, rsp);
				rsp.send(views.Auth.createAccountSuccess.template(account.getUsername()));

				req.require(Mods.class).send(email -> email
						.setSubject("New user " + account.getUsername())
						.setMsg(Mods.table(
								"Username", link.getUsername(),
								"Email", link.getEmail(),
								"User id", account.getId().toString(),
								"Link", "https://mytake.org/" + account.getUsername())));
			}
		}
	}
}
