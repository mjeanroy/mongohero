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

import com.github.mjeanroy.mongohero.core.model.Database;
import com.github.mjeanroy.mongohero.core.mongo.Mongo;
import com.github.mjeanroy.mongohero.core.mongo.MongoMapper;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.stream.Stream;

@Repository
public class DatabaseRepository {

	private final Mongo mongo;
	private final MongoMapper mongoMapper;

	@Autowired
	DatabaseRepository(Mongo mongo, MongoMapper mongoMapper) {
		this.mongo = mongo;
		this.mongoMapper = mongoMapper;
	}

	/**
	 * List all databases, <strong>except {@code "admin"} and {@code "local"} (a.k.a mongo internal databases).</strong>
	 *
	 * @return All databases.
	 */
	public Stream<Database> listDatabases() {
		return mongo.listDatabases().map(this::extendDocument).map(document -> mongoMapper.map(document, Database.class));
	}

	/**
	 * Get database from its name.
	 *
	 * @param databaseName The database name.
	 * @return The database.
	 */
	public Database getDatabase(String databaseName) {
		Document document = mongo.database(databaseName)
				.map(this::extendDocument)
				.orElseGet(() -> createEmptyDocument(databaseName));

		return mongoMapper.map(document, Database.class);
	}

	private Document extendDocument(Document document) {
		String databaseName = document.get("name", String.class);

		Document extendedDocument = new Document();
		extendedDocument.putAll(document);
		extendedDocument.put("stats", getStats(databaseName));
		return extendedDocument;
	}

	private Document createEmptyDocument(String databaseName) {
		Document stats = new Document();
		stats.put("db", databaseName);

		Document document = new Document();
		document.put("name", databaseName);
		document.put("empty", true);
		document.put("stats", stats);
		return document;
	}

	private Document getStats(String databaseName) {
		return mongo.dbstats(databaseName);
	}
}
