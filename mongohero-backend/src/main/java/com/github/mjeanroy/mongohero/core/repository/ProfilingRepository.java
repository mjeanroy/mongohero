/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2019 Mickael Jeanroy
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.github.mjeanroy.mongohero.core.repository;

import com.github.mjeanroy.mongohero.core.model.ProfileQuery;
import com.github.mjeanroy.mongohero.core.model.ProfilingStatus;
import com.github.mjeanroy.mongohero.core.mongo.Mongo;
import com.github.mjeanroy.mongohero.core.mongo.MongoMapper;
import com.github.mjeanroy.mongohero.core.mongo.MongoPage;
import com.github.mjeanroy.mongohero.core.query.Page;
import com.github.mjeanroy.mongohero.core.query.PageResult;
import com.github.mjeanroy.mongohero.core.query.ProfileQueryFilter;
import com.github.mjeanroy.mongohero.core.query.Sort;
import com.mongodb.BasicDBObject;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.mongodb.MongoNamespace.checkDatabaseNameValidity;

@Repository
public class ProfilingRepository {

	private final Mongo mongo;
	private final MongoMapper mongoMapper;

	@Autowired
	ProfilingRepository(Mongo mongo, MongoMapper mongoMapper) {
		this.mongo = mongo;
		this.mongoMapper = mongoMapper;
	}

	/**
	 * Get profiling status for given database.
	 *
	 * @param db The database name.
	 * @return The profiling status.
	 */
	public ProfilingStatus getStatus(String db) {
		Document document = mongo.getProfilingLevel(db);
		return mongoMapper.map(document, ProfilingStatus.class);
	}

	/**
	 * Update profiling level for given database.
	 *
	 * @param db     Database name.
	 * @param level  Profiling level.
	 * @param slowMs Slow queries threshold.
	 */
	public void setStatus(String db, int level, int slowMs) {
		mongo.setProfilingLevel(db, level, slowMs);
	}

	/**
	 * Find slow queries for given database.
	 *
	 * @param database Database Name.
	 * @param filter   The filter to apply.
	 * @param page     The page to query (must not be {@code null}).
	 * @param sort     The sort to apply (must not be {@code null}).
	 * @return The page results.
	 */
	public Map<String, PageResult<ProfileQuery>> findSlowQueries(String database, ProfileQueryFilter filter, Page page, Sort sort) {
		checkDatabaseNameValidity(database);

		final int offset = page.getOffset();
		final int limit = page.getPageSize();
		final BasicDBObject mongoFilters = toMongoFilters(
				filter.toBuilder()
						.addBlacklistedNs(database + ".system.profile")
						.build()
		);

		final Document mongoSort = new Document(sort.getName(), sort.order());
		final Map<String, MongoPage> mongoPages = mongo.findSystemProfile(database, mongoFilters, offset, limit, mongoSort);
		return mongoPages.entrySet().stream().collect(Collectors.toMap(
				Map.Entry::getKey,
				entry -> toPageResult(page, sort, entry.getValue())
		));
	}

	private PageResult<ProfileQuery> toPageResult(Page page, Sort sort, MongoPage mongoPage) {
		final Stream<ProfileQuery> results = mongoPage.stream().map(document -> mongoMapper.map(document, ProfileQuery.class));
		final long total = mongoPage.getTotal();
		return PageResult.of(results, page, sort, total);
	}

	/**
	 * Remove all queries currently stored in {@code "system.profile"} collection for
	 * given database.
	 *
	 * @param database Database.
	 */
	public void removeSlowQueries(String database) {
		mongo.dropSystemProfile(database);
	}

	private static BasicDBObject toMongoFilters(ProfileQueryFilter filters) {
		BasicDBObject mongoFilter = new BasicDBObject();

		String op = filters.getOp();
		if (op != null && !op.isEmpty()) {
			mongoFilter.append("op", op);
		}

		Set<String> blacklistCollections = filters.getNsBlacklist();
		if (!blacklistCollections.isEmpty()) {
			mongoFilter.append("ns", new BasicDBObject("$nin", filters.getNsBlacklist()));
		}

		return mongoFilter;
	}
}
