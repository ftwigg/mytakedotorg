/*
 * MyTake.org website and tooling.
 * Copyright (C) 2018-2020 MyTake.org, Inc.
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
package common;

import static db.Tables.FOUNDATION_REV;
import static db.Tables.TAKEPUBLISHED;
import static db.Tables.TAKEREVISION;

import com.google.common.collect.ImmutableSortedMap;
import com.google.common.primitives.Ints;
import com.google.inject.Binder;
import com.typesafe.config.Config;
import db.tables.records.FoundationRevRecord;
import db.tables.records.TakepublishedRecord;
import db.tables.records.TakerevisionRecord;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.jooby.Env;
import org.jooby.Jooby;
import org.jooq.DSLContext;
import org.jooq.JSONB;
import org.jooq.Result;
import org.jooq.impl.DSL;

public class FoundationMigrationModule implements Jooby.Module {
	static int maxId() {
		return MIGRATIONS.keySet().last();
	}

	private static final ImmutableSortedMap<Integer, FoundationMigration> MIGRATIONS = ImmutableSortedMap.of(
			2, FoundationMigration.createReplacing("V2__video_duration_and_encode"),
			3, FoundationMigration.createReplacing("V3__video_new_transcripts"),
			4, FoundationMigration.createReplacing("V4__kennedy_nixon_transcript_1_of_4"));

	@Override
	public void configure(Env env, Config conf, Binder binder) throws Throwable {
		env.onStart(registry -> {
			Time time = registry.require(Time.class);
			try (DSLContext dsl = registry.require(DSLContext.class)) {
				migrate(dsl, time);
			}
		});
	}

	static Set<Integer> migrate(DSLContext dsl, Time time) {
		int latestRev = dsl.selectFrom(FOUNDATION_REV)
				.orderBy(FOUNDATION_REV.VERSION.desc())
				.limit(1)
				.fetch(FOUNDATION_REV.VERSION)
				.get(0);
		Set<Integer> publishedTakesToRefresh = new HashSet<>();
		ImmutableSortedMap<Integer, FoundationMigration> toMigrate = MIGRATIONS.tailMap(latestRev + 1);
		toMigrate.forEach((version, migration) -> {
			dsl.transaction(configuration -> {
				try (DSLContext updateDocs = DSL.using(configuration)) {
					List<Integer> needsRefresh = migrate(updateDocs, time, version, migration);
					publishedTakesToRefresh.addAll(needsRefresh);
				}
			});
		});
		return publishedTakesToRefresh;
	}

	private static List<Integer> migrate(DSLContext dsl, Time time, int version, FoundationMigration migration) {
		long startMs = time.nowMs();
		// update take drafts
		Result<TakerevisionRecord> revs = dsl.fetch(TAKEREVISION);
		for (TakerevisionRecord rev : revs) {
			JSONB original = rev.getBlocks();
			JSONB migrated = migration.migrate(original);
			if (!original.equals(migrated)) {
				rev.setBlocks(migrated);
				rev.store();
			}
		}

		// update published takes and remember the ids we updated
		List<Integer> takeIdsToRefresh = new ArrayList<>();
		Result<TakepublishedRecord> publisheds = dsl.fetch(TAKEPUBLISHED);
		for (TakepublishedRecord published : publisheds) {
			JSONB original = published.getBlocks();
			JSONB migrated = migration.migrate(original);
			if (!original.equals(migrated)) {
				published.setBlocks(migrated);
				published.store();
				takeIdsToRefresh.add(published.getId());
			}
		}
		int elapsed = Ints.saturatedCast((time.nowMs() - startMs) / 1000L);

		// add a record of our migration
		FoundationRevRecord record = dsl.newRecord(FOUNDATION_REV);
		record.setVersion(version);
		record.setDescription(migration.description());
		record.setMigratedOn(time.nowTimestamp());
		record.setExecutionTimeSec(elapsed);
		record.insert();

		return takeIdsToRefresh;
	}
}
