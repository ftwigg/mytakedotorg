/*
 * This file is generated by jOOQ.
 */
package db;


import db.tables.Account;
import db.tables.Confirmaccountlink;
import db.tables.FlywaySchemaHistory;
import db.tables.Follow;
import db.tables.FoundationRev;
import db.tables.Loginlink;
import db.tables.Moderator;
import db.tables.SharedFacts;
import db.tables.SharedUrlRev;
import db.tables.Takedraft;
import db.tables.Takepublished;
import db.tables.Takereaction;
import db.tables.Takerevision;
import db.tables.records.AccountRecord;
import db.tables.records.ConfirmaccountlinkRecord;
import db.tables.records.FlywaySchemaHistoryRecord;
import db.tables.records.FollowRecord;
import db.tables.records.FoundationRevRecord;
import db.tables.records.LoginlinkRecord;
import db.tables.records.ModeratorRecord;
import db.tables.records.SharedFactsRecord;
import db.tables.records.SharedUrlRevRecord;
import db.tables.records.TakedraftRecord;
import db.tables.records.TakepublishedRecord;
import db.tables.records.TakereactionRecord;
import db.tables.records.TakerevisionRecord;

import org.jooq.ForeignKey;
import org.jooq.Identity;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.Internal;


/**
 * A class modelling foreign key relationships and constraints of tables of 
 * the <code>public</code> schema.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Keys {

    // -------------------------------------------------------------------------
    // IDENTITY definitions
    // -------------------------------------------------------------------------

    public static final Identity<AccountRecord, Integer> IDENTITY_ACCOUNT = Identities0.IDENTITY_ACCOUNT;
    public static final Identity<TakedraftRecord, Integer> IDENTITY_TAKEDRAFT = Identities0.IDENTITY_TAKEDRAFT;
    public static final Identity<TakepublishedRecord, Integer> IDENTITY_TAKEPUBLISHED = Identities0.IDENTITY_TAKEPUBLISHED;
    public static final Identity<TakerevisionRecord, Integer> IDENTITY_TAKEREVISION = Identities0.IDENTITY_TAKEREVISION;

    // -------------------------------------------------------------------------
    // UNIQUE and PRIMARY KEY definitions
    // -------------------------------------------------------------------------

    public static final UniqueKey<AccountRecord> ACCOUNT_PKEY = UniqueKeys0.ACCOUNT_PKEY;
    public static final UniqueKey<AccountRecord> ACCOUNT_USERNAME_KEY = UniqueKeys0.ACCOUNT_USERNAME_KEY;
    public static final UniqueKey<AccountRecord> ACCOUNT_EMAIL_KEY = UniqueKeys0.ACCOUNT_EMAIL_KEY;
    public static final UniqueKey<ConfirmaccountlinkRecord> CONFIRMACCOUNTLINK_PKEY = UniqueKeys0.CONFIRMACCOUNTLINK_PKEY;
    public static final UniqueKey<FlywaySchemaHistoryRecord> FLYWAY_SCHEMA_HISTORY_PK = UniqueKeys0.FLYWAY_SCHEMA_HISTORY_PK;
    public static final UniqueKey<FollowRecord> FOLLOW_PKEY = UniqueKeys0.FOLLOW_PKEY;
    public static final UniqueKey<FoundationRevRecord> FOUNDATION_REV_PKEY = UniqueKeys0.FOUNDATION_REV_PKEY;
    public static final UniqueKey<LoginlinkRecord> LOGINLINK_PKEY = UniqueKeys0.LOGINLINK_PKEY;
    public static final UniqueKey<ModeratorRecord> MODERATOR_PKEY = UniqueKeys0.MODERATOR_PKEY;
    public static final UniqueKey<SharedUrlRevRecord> SHARED_URL_REV_PKEY = UniqueKeys0.SHARED_URL_REV_PKEY;
    public static final UniqueKey<TakedraftRecord> TAKEDRAFT_PKEY = UniqueKeys0.TAKEDRAFT_PKEY;
    public static final UniqueKey<TakepublishedRecord> TAKEPUBLISHED_PKEY = UniqueKeys0.TAKEPUBLISHED_PKEY;
    public static final UniqueKey<TakereactionRecord> TAKEREACTION_PKEY = UniqueKeys0.TAKEREACTION_PKEY;
    public static final UniqueKey<TakerevisionRecord> TAKEREVISION_PKEY = UniqueKeys0.TAKEREVISION_PKEY;

    // -------------------------------------------------------------------------
    // FOREIGN KEY definitions
    // -------------------------------------------------------------------------

    public static final ForeignKey<FollowRecord, AccountRecord> FOLLOW__FOLLOW_AUTHOR_FKEY = ForeignKeys0.FOLLOW__FOLLOW_AUTHOR_FKEY;
    public static final ForeignKey<FollowRecord, AccountRecord> FOLLOW__FOLLOW_FOLLOWER_FKEY = ForeignKeys0.FOLLOW__FOLLOW_FOLLOWER_FKEY;
    public static final ForeignKey<LoginlinkRecord, AccountRecord> LOGINLINK__LOGINLINK_ACCOUNT_ID_FKEY = ForeignKeys0.LOGINLINK__LOGINLINK_ACCOUNT_ID_FKEY;
    public static final ForeignKey<SharedFactsRecord, AccountRecord> SHARED_FACTS__SHARED_FACTS_SHARED_BY_FKEY = ForeignKeys0.SHARED_FACTS__SHARED_FACTS_SHARED_BY_FKEY;
    public static final ForeignKey<SharedFactsRecord, SharedUrlRevRecord> SHARED_FACTS__SHARED_FACTS_URL_VERSION_FKEY = ForeignKeys0.SHARED_FACTS__SHARED_FACTS_URL_VERSION_FKEY;
    public static final ForeignKey<TakedraftRecord, AccountRecord> TAKEDRAFT__TAKEDRAFT_USER_ID_FKEY = ForeignKeys0.TAKEDRAFT__TAKEDRAFT_USER_ID_FKEY;
    public static final ForeignKey<TakedraftRecord, TakerevisionRecord> TAKEDRAFT__TAKEDRAFT_LAST_REVISION_FKEY = ForeignKeys0.TAKEDRAFT__TAKEDRAFT_LAST_REVISION_FKEY;
    public static final ForeignKey<TakepublishedRecord, AccountRecord> TAKEPUBLISHED__TAKEPUBLISHED_USER_ID_FKEY = ForeignKeys0.TAKEPUBLISHED__TAKEPUBLISHED_USER_ID_FKEY;
    public static final ForeignKey<TakereactionRecord, TakepublishedRecord> TAKEREACTION__TAKEREACTION_TAKE_ID_FKEY = ForeignKeys0.TAKEREACTION__TAKEREACTION_TAKE_ID_FKEY;
    public static final ForeignKey<TakereactionRecord, AccountRecord> TAKEREACTION__TAKEREACTION_USER_ID_FKEY = ForeignKeys0.TAKEREACTION__TAKEREACTION_USER_ID_FKEY;
    public static final ForeignKey<TakerevisionRecord, TakerevisionRecord> TAKEREVISION__TAKEREVISION_PARENT_ID_FKEY = ForeignKeys0.TAKEREVISION__TAKEREVISION_PARENT_ID_FKEY;

    // -------------------------------------------------------------------------
    // [#1459] distribute members to avoid static initialisers > 64kb
    // -------------------------------------------------------------------------

    private static class Identities0 {
        public static Identity<AccountRecord, Integer> IDENTITY_ACCOUNT = Internal.createIdentity(Account.ACCOUNT, Account.ACCOUNT.ID);
        public static Identity<TakedraftRecord, Integer> IDENTITY_TAKEDRAFT = Internal.createIdentity(Takedraft.TAKEDRAFT, Takedraft.TAKEDRAFT.ID);
        public static Identity<TakepublishedRecord, Integer> IDENTITY_TAKEPUBLISHED = Internal.createIdentity(Takepublished.TAKEPUBLISHED, Takepublished.TAKEPUBLISHED.ID);
        public static Identity<TakerevisionRecord, Integer> IDENTITY_TAKEREVISION = Internal.createIdentity(Takerevision.TAKEREVISION, Takerevision.TAKEREVISION.ID);
    }

    private static class UniqueKeys0 {
        public static final UniqueKey<AccountRecord> ACCOUNT_PKEY = Internal.createUniqueKey(Account.ACCOUNT, "account_pkey", new TableField[] { Account.ACCOUNT.ID }, true);
        public static final UniqueKey<AccountRecord> ACCOUNT_USERNAME_KEY = Internal.createUniqueKey(Account.ACCOUNT, "account_username_key", new TableField[] { Account.ACCOUNT.USERNAME }, true);
        public static final UniqueKey<AccountRecord> ACCOUNT_EMAIL_KEY = Internal.createUniqueKey(Account.ACCOUNT, "account_email_key", new TableField[] { Account.ACCOUNT.EMAIL }, true);
        public static final UniqueKey<ConfirmaccountlinkRecord> CONFIRMACCOUNTLINK_PKEY = Internal.createUniqueKey(Confirmaccountlink.CONFIRMACCOUNTLINK, "confirmaccountlink_pkey", new TableField[] { Confirmaccountlink.CONFIRMACCOUNTLINK.CODE }, true);
        public static final UniqueKey<FlywaySchemaHistoryRecord> FLYWAY_SCHEMA_HISTORY_PK = Internal.createUniqueKey(FlywaySchemaHistory.FLYWAY_SCHEMA_HISTORY, "flyway_schema_history_pk", new TableField[] { FlywaySchemaHistory.FLYWAY_SCHEMA_HISTORY.INSTALLED_RANK }, true);
        public static final UniqueKey<FollowRecord> FOLLOW_PKEY = Internal.createUniqueKey(Follow.FOLLOW, "follow_pkey", new TableField[] { Follow.FOLLOW.AUTHOR, Follow.FOLLOW.FOLLOWER }, true);
        public static final UniqueKey<FoundationRevRecord> FOUNDATION_REV_PKEY = Internal.createUniqueKey(FoundationRev.FOUNDATION_REV, "foundation_rev_pkey", new TableField[] { FoundationRev.FOUNDATION_REV.VERSION }, true);
        public static final UniqueKey<LoginlinkRecord> LOGINLINK_PKEY = Internal.createUniqueKey(Loginlink.LOGINLINK, "loginlink_pkey", new TableField[] { Loginlink.LOGINLINK.CODE }, true);
        public static final UniqueKey<ModeratorRecord> MODERATOR_PKEY = Internal.createUniqueKey(Moderator.MODERATOR, "moderator_pkey", new TableField[] { Moderator.MODERATOR.ID }, true);
        public static final UniqueKey<SharedUrlRevRecord> SHARED_URL_REV_PKEY = Internal.createUniqueKey(SharedUrlRev.SHARED_URL_REV, "shared_url_rev_pkey", new TableField[] { SharedUrlRev.SHARED_URL_REV.VERSION }, true);
        public static final UniqueKey<TakedraftRecord> TAKEDRAFT_PKEY = Internal.createUniqueKey(Takedraft.TAKEDRAFT, "takedraft_pkey", new TableField[] { Takedraft.TAKEDRAFT.ID }, true);
        public static final UniqueKey<TakepublishedRecord> TAKEPUBLISHED_PKEY = Internal.createUniqueKey(Takepublished.TAKEPUBLISHED, "takepublished_pkey", new TableField[] { Takepublished.TAKEPUBLISHED.ID }, true);
        public static final UniqueKey<TakereactionRecord> TAKEREACTION_PKEY = Internal.createUniqueKey(Takereaction.TAKEREACTION, "takereaction_pkey", new TableField[] { Takereaction.TAKEREACTION.TAKE_ID, Takereaction.TAKEREACTION.USER_ID, Takereaction.TAKEREACTION.KIND }, true);
        public static final UniqueKey<TakerevisionRecord> TAKEREVISION_PKEY = Internal.createUniqueKey(Takerevision.TAKEREVISION, "takerevision_pkey", new TableField[] { Takerevision.TAKEREVISION.ID }, true);
    }

    private static class ForeignKeys0 {
        public static final ForeignKey<FollowRecord, AccountRecord> FOLLOW__FOLLOW_AUTHOR_FKEY = Internal.createForeignKey(Keys.ACCOUNT_PKEY, Follow.FOLLOW, "follow_author_fkey", new TableField[] { Follow.FOLLOW.AUTHOR }, true);
        public static final ForeignKey<FollowRecord, AccountRecord> FOLLOW__FOLLOW_FOLLOWER_FKEY = Internal.createForeignKey(Keys.ACCOUNT_PKEY, Follow.FOLLOW, "follow_follower_fkey", new TableField[] { Follow.FOLLOW.FOLLOWER }, true);
        public static final ForeignKey<LoginlinkRecord, AccountRecord> LOGINLINK__LOGINLINK_ACCOUNT_ID_FKEY = Internal.createForeignKey(Keys.ACCOUNT_PKEY, Loginlink.LOGINLINK, "loginlink_account_id_fkey", new TableField[] { Loginlink.LOGINLINK.ACCOUNT_ID }, true);
        public static final ForeignKey<SharedFactsRecord, AccountRecord> SHARED_FACTS__SHARED_FACTS_SHARED_BY_FKEY = Internal.createForeignKey(Keys.ACCOUNT_PKEY, SharedFacts.SHARED_FACTS, "shared_facts_shared_by_fkey", new TableField[] { SharedFacts.SHARED_FACTS.SHARED_BY }, true);
        public static final ForeignKey<SharedFactsRecord, SharedUrlRevRecord> SHARED_FACTS__SHARED_FACTS_URL_VERSION_FKEY = Internal.createForeignKey(Keys.SHARED_URL_REV_PKEY, SharedFacts.SHARED_FACTS, "shared_facts_url_version_fkey", new TableField[] { SharedFacts.SHARED_FACTS.URL_VERSION }, true);
        public static final ForeignKey<TakedraftRecord, AccountRecord> TAKEDRAFT__TAKEDRAFT_USER_ID_FKEY = Internal.createForeignKey(Keys.ACCOUNT_PKEY, Takedraft.TAKEDRAFT, "takedraft_user_id_fkey", new TableField[] { Takedraft.TAKEDRAFT.USER_ID }, true);
        public static final ForeignKey<TakedraftRecord, TakerevisionRecord> TAKEDRAFT__TAKEDRAFT_LAST_REVISION_FKEY = Internal.createForeignKey(Keys.TAKEREVISION_PKEY, Takedraft.TAKEDRAFT, "takedraft_last_revision_fkey", new TableField[] { Takedraft.TAKEDRAFT.LAST_REVISION }, true);
        public static final ForeignKey<TakepublishedRecord, AccountRecord> TAKEPUBLISHED__TAKEPUBLISHED_USER_ID_FKEY = Internal.createForeignKey(Keys.ACCOUNT_PKEY, Takepublished.TAKEPUBLISHED, "takepublished_user_id_fkey", new TableField[] { Takepublished.TAKEPUBLISHED.USER_ID }, true);
        public static final ForeignKey<TakereactionRecord, TakepublishedRecord> TAKEREACTION__TAKEREACTION_TAKE_ID_FKEY = Internal.createForeignKey(Keys.TAKEPUBLISHED_PKEY, Takereaction.TAKEREACTION, "takereaction_take_id_fkey", new TableField[] { Takereaction.TAKEREACTION.TAKE_ID }, true);
        public static final ForeignKey<TakereactionRecord, AccountRecord> TAKEREACTION__TAKEREACTION_USER_ID_FKEY = Internal.createForeignKey(Keys.ACCOUNT_PKEY, Takereaction.TAKEREACTION, "takereaction_user_id_fkey", new TableField[] { Takereaction.TAKEREACTION.USER_ID }, true);
        public static final ForeignKey<TakerevisionRecord, TakerevisionRecord> TAKEREVISION__TAKEREVISION_PARENT_ID_FKEY = Internal.createForeignKey(Keys.TAKEREVISION_PKEY, Takerevision.TAKEREVISION, "takerevision_parent_id_fkey", new TableField[] { Takerevision.TAKEREVISION.PARENT_ID }, true);
    }
}
