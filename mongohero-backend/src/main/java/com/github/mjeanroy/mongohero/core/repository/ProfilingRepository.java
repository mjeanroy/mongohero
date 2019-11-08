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
import com.github.mjeanroy.mongohero.core.query.Page;
import com.github.mjeanroy.mongohero.core.query.PageResult;
import com.github.mjeanroy.mongohero.core.query.ProfileQueryFilter;
import com.github.mjeanroy.mongohero.core.query.Sort;
import com.github.mjeanroy.mongohero.mongo.MongoMapper;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.stream.Stream;

import static com.github.mjeanroy.mongohero.commons.Streams.toStream;
import static java.util.Collections.emptySet;

@Repository
public class ProfilingRepository {

    private final MongoClient mongoClient;
    private final MongoMapper mongoMapper;

    @Autowired
    ProfilingRepository(MongoClient mongoClient, MongoMapper mongoMapper) {
        this.mongoClient = mongoClient;
        this.mongoMapper = mongoMapper;
    }

    /**
     * Get profiling status for given database.
     *
     * @param db The database name.
     * @return The profiling status.
     */
    public ProfilingStatus getStatus(String db) {
        Document query = new Document("profile", -1);
        Document document = mongoClient.getDatabase(db).runCommand(query);
        return mongoMapper.map(document, ProfilingStatus.class);
    }

    /**
     * Update profiling level for given database.
     *
     * @param db Database name.
     * @param level Profiling level.
     * @param slowMs Slow queries threshold.
     */
    public void setStatus(String db, int level, int slowMs) {
        Document query = new Document();
        query.put("profile", level);
        query.put("slowms", slowMs);

        mongoClient.getDatabase(db).runCommand(query);
    }

    /**
     * Remove all queries currently stored in {@code "system.profile"} collection for
     * given database.
     *
     * @param database Database.
     */
    public void removeSlowQueries(String database) {
        MongoDatabase systemDb = mongoClient.getDatabase(database);
        MongoCollection<Document> collection = systemDb.getCollection("system.profile");
        collection.drop();
    }

    public PageResult<ProfileQuery> findSlowQueries(String database, ProfileQueryFilter filter, Page page, Sort sort) {
        final MongoDatabase systemDb = mongoClient.getDatabase(database);
        final MongoCollection<Document> collection = systemDb.getCollection("system.profile");
        final BasicDBObject mongoFilters = toMongoFiler(filter);
        final long total = collection.countDocuments(mongoFilters);

        final int offset = page.getOffset();
        final Iterable<Document> documents;
        if (total > 0 && offset <= total) {
            documents = collection.find(mongoFilters).sort(new Document(sort.getName(), sort.order())).skip(offset).limit(page.getPageSize());
        } else {
            documents = emptySet();
        }

        Stream<ProfileQuery> results = toStream(documents).map(document -> mongoMapper.map(document, ProfileQuery.class));
        return PageResult.of(results, page, sort, total);
    }

    private BasicDBObject toMongoFiler(ProfileQueryFilter filter) {
        BasicDBObject mongoFilter = new BasicDBObject();

        String op = filter.getOp();
        if (op != null && !op.isEmpty()) {
            mongoFilter.append("op", op);
        }

        return mongoFilter;
    }
}
