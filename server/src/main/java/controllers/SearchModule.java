/*
 * MyTake.org
 *
 *  Copyright 2017 by its authors.
 *  Some rights reserved. See LICENSE, https://github.com/mytakedotorg/mytakedotorg/graphs/contributors
 */
package controllers;

import com.google.inject.Binder;
import com.typesafe.config.Config;
import java2ts.Routes;
import java2ts.Search;
import org.jooby.Env;
import org.jooby.Jooby;
import org.jooby.Results;
import org.mytake.lucene.Lucene;

public class SearchModule implements Jooby.Module {
	@Override
	public void configure(Env env, Config conf, Binder binder) throws Throwable {
		Lucene lucene = Lucene.openFromArchive();
		env.onStop(() -> {
			lucene.close();
		});
		env.router().post(Routes.API_SEARCH, req -> {
			Search.Request request = req.body(Search.Request.class);
			return lucene.searchDebate(request);
		});
		env.router().get(Routes.SEARCH, req -> {
			Search.Request request = req.params(Search.Request.class);
			if (request.q == null) {
				return Results.redirect(Routes.FOUNDATION);
			} else {
				return views.Search.searchResults.template(request.q);
			}
		});
	}
}
