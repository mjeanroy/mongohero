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
import com.github.mjeanroy.mongohero.mongo.MongoMapper;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Repository
public class CollectionRepository {

    private final MongoClient mongoClient;
    private final MongoMapper mongoMapper;

    @Autowired
    CollectionRepository(
            MongoClient mongoClient,
            MongoMapper mongoMapper) {

        this.mongoClient = mongoClient;
        this.mongoMapper = mongoMapper;
    }

    public Stream<Collection> findAll(String database) {
        MongoDatabase database1 = mongoClient.getDatabase(database);
        Iterable<Document> collections = database1.listCollections();
        return StreamSupport.stream(collections.spliterator(), false)
                .map(document -> toCollectionWithNs(database, document))
                .map(document -> mongoMapper.map(document, Collection.class));
    }

    private Document toCollectionWithNs(String database, Document document) {
        Document extendedDocument = new Document();
        extendedDocument.putAll(document);
        extendedDocument.put("ns", database + "." + document.get("name"));
        return extendedDocument;
    }

    public CollectionStats findStats(String database, String collection) {
        Document document = mongoClient.getDatabase(database).runCommand(new Document("collStats", collection));
        return mongoMapper.map(document, CollectionStats.class);
    }
}
