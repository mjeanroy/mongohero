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

import com.github.mjeanroy.mongohero.commons.Streams;
import com.github.mjeanroy.mongohero.core.model.Database;
import com.github.mjeanroy.mongohero.mongo.MongoMapper;
import com.mongodb.client.MongoClient;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

@Repository
public class DatabaseRepository {

    private final MongoClient mongoClient;
    private final MongoMapper mongoMapper;

    @Autowired
    DatabaseRepository(
            MongoClient mongoClient,
            MongoMapper mongoMapper) {

        this.mongoClient = mongoClient;
        this.mongoMapper = mongoMapper;
    }

    public Stream<Database> findAll() {
        return listDatabases()
                .filter(document -> !Objects.equals(document.get("name"), "admin"))
                .filter(document -> !Objects.equals(document.get("name"), "local"))
                .map(this::extendDocument)
                .map(document -> mongoMapper.map(document, Database.class));
    }

    public Optional<Database> findOne(String name) {
        return listDatabases()
                .filter(document -> Objects.equals(document.get("name"), name))
                .map(this::extendDocument)
                .map(document -> mongoMapper.map(document, Database.class))
                .findFirst();
    }

    private Stream<Document> listDatabases() {
        Iterable<Document> dbs = mongoClient.listDatabases();
        return Streams.toStream(dbs);
    }

    private Document extendDocument(Document document) {
        String databaseName = document.get("name", String.class);

        Document extendedDocument = new Document();
        extendedDocument.putAll(document);
        extendedDocument.put("stats", getStats(databaseName));
        return extendedDocument;
    }

    private Document getStats(String name) {
        return mongoClient.getDatabase(name).runCommand(new Document("dbstats", 1));
    }
}
