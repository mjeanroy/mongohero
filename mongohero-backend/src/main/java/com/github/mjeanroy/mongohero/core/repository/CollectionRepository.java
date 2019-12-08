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

import com.github.mjeanroy.mongohero.core.model.Collection;
import com.github.mjeanroy.mongohero.core.model.CollectionStats;
import com.github.mjeanroy.mongohero.core.model.IndexStat;
import com.github.mjeanroy.mongohero.core.mongo.Mongo;
import com.github.mjeanroy.mongohero.core.mongo.MongoMapper;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Stream;

@Repository
public class CollectionRepository {

	private static final Logger log = LoggerFactory.getLogger(CollectionRepository.class);

	private final Mongo mongo;
	private final MongoMapper mongoMapper;

	@Autowired
	CollectionRepository(Mongo mongo, MongoMapper mongoMapper) {
		this.mongo = mongo;
		this.mongoMapper = mongoMapper;
	}

	/**
	 * Finds all the collections in this database.
	 *
	 * @return the list collections iterable interface
	 */
	public Stream<Collection> listCollections(String database) {
		return mongo.listCollections(database)
				.map(document -> toCollectionWithNs(database, document))
				.map(document -> mongoMapper.map(document, Collection.class));
	}

	/**
	 * Read collection statistics on given database.
	 *
	 * @param database Database name.
	 * @param collection Collection name.
	 * @return Collection statistics.
	 */
	public CollectionStats collStats(String database, String collection) {
		Document document = mongo.collStats(database, collection);
		return mongoMapper.map(document, CollectionStats.class);
	}

	/**
	 * Read index statistics on given collection.
	 *
	 * @param database Database name.
	 * @param collection Collection name.
	 * @return Index statistics.
	 */
	public Stream<IndexStat> indexStats(String database, String collection) {
		Map<String, Stream<Document>> results = mongo.indexStats(database, collection);

		Map<String, IndexStat> aggregatedStats = new LinkedHashMap<>();

		for (Stream<Document> documents : results.values()) {
			documents.forEach(doc -> {
				IndexStat indexStat = mongoMapper.map(doc, IndexStat.class);
				String name = indexStat.getName();

				IndexStat current = aggregatedStats.get(name);

				if (current == null) {
					log.debug("Adding new index stat: {}", indexStat);
					aggregatedStats.put(name, indexStat);
				}
				else {
					log.debug("Merging index stat {} with {}", current, indexStat);
					aggregatedStats.put(name, current.merge(indexStat));
				}
			});
		}

		return aggregatedStats.values().stream();
	}

	private Document toCollectionWithNs(String database, Document document) {
		Document extendedDocument = new Document();
		extendedDocument.putAll(document);
		extendedDocument.put("ns", database + "." + document.get("name"));
		return extendedDocument;
	}
}
