/**
 * The MIT License (MIT)
 * <p>
 * Copyright (c) 2019 Mickael Jeanroy
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.github.mjeanroy.mongohero.tests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClientSettings;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.junit.jupiter.api.extension.*;
import org.junit.jupiter.api.extension.ExtensionContext.Namespace;
import org.testcontainers.containers.GenericContainer;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.*;

import static org.junit.platform.commons.support.AnnotationSupport.findAnnotation;

class MongoDbExtension implements BeforeAllCallback, BeforeEachCallback, AfterAllCallback, AfterEachCallback, ParameterResolver {

    private static final Namespace NAMESPACE = Namespace.create(MongoDbExtension.class);
    private static final String CONTAINER_KEy = "MONGO_DB_CONTAINER";
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void beforeAll(ExtensionContext context) throws Exception {
        Class<?> testClass = context.getRequiredTestClass();
        MongoDbTest mongoDbTest = findAnnotation(testClass, MongoDbTest.class).orElseThrow(() ->
                new AssertionError("Cannot find @MongoDbTest annotation")
        );

        String version = mongoDbTest.version();
        GenericContainer container = new GenericContainer("mongo:" + version).withExposedPorts(27017);
        container.start();

        context.getStore(NAMESPACE).put(CONTAINER_KEy, container);
    }

    @Override
    public void afterAll(ExtensionContext context) {
        ExtensionContext.Store store = context.getStore(NAMESPACE);
        GenericContainer container = store.get(CONTAINER_KEy, GenericContainer.class);

        try {
            container.stop();
        } finally {
            store.remove(CONTAINER_KEy);
        }
    }

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return MongoClient.class.isAssignableFrom(parameterContext.getParameter().getType());
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return createMongoClient(extensionContext);
    }

    @Override
    public void beforeEach(ExtensionContext context) {
        findMongoDbDatasetAnnotation(context).ifPresent(annotation ->
                loadDataSets(annotation.dataset(), context)
        );
    }

    @Override
    public void afterEach(ExtensionContext context) {
        findMongoDbDatasetAnnotation(context).ifPresent(annotation ->
                clearDataSets(annotation.dataset(), context)
        );
    }

    private static Optional<MongoDbDataset> findMongoDbDatasetAnnotation(ExtensionContext context) {
        Optional<MongoDbDataset> maybeAnnotation = findAnnotation(context.getRequiredTestMethod(), MongoDbDataset.class);
        if (!maybeAnnotation.isPresent()) {
            maybeAnnotation = findAnnotation(context.getRequiredTestClass(), MongoDbDataset.class);
        }

        return maybeAnnotation;
    }

    private static List<ServerAddress> buildMongoDbHosts(int port) {
        return Collections.singletonList(buildMongoDbHost(port));
    }

    private static ServerAddress buildMongoDbHost(int port) {
        return new ServerAddress("localhost", port);
    }

    private static void loadDataSets(String[] datasets, ExtensionContext extensionContext) {
        Arrays.stream(datasets).forEach(dataset ->
                loadDataSet(dataset, extensionContext)
        );
    }

    private static void clearDataSets(String[] datasets, ExtensionContext extensionContext) {
        Arrays.stream(datasets).forEach(dataset ->
                clearDataSet(dataset, extensionContext)
        );
    }

    private static void clearDataSet(String dataset, ExtensionContext extensionContext) {
        createMongoClient(extensionContext).getDatabase(readDataSet(dataset).dbName).drop();
    }

    private static void loadDataSet(String dataset, ExtensionContext extensionContext) {
        MongoClient client = createMongoClient(extensionContext);

        DataSet dataSet = readDataSet(dataset);
        String dbName = dataSet.dbName;
        MongoDatabase mongoDatabase = client.getDatabase(dbName);

        dataSet.documents.forEach((collection, documents) -> {
            MongoCollection<Document> mongoCollection = mongoDatabase.getCollection(collection);
            documents.forEach(document ->
                    insertJsonDocument(mongoCollection, document)
            );
        });
    }

    @SuppressWarnings("unchecked")
    private static DataSet readDataSet(String dataset) {
        URL url = MongoDbExtension.class.getResource(dataset);
        String name = Paths.get(url.getPath()).toFile().getName();
        String dbName = name.substring(0, name.lastIndexOf('.'));

        try {
            Map<String, List<Map<String, Object>>> documents = (Map<String, List<Map<String, Object>>>) objectMapper.readValue(url, Map.class);
            return new DataSet(dbName, documents);
        } catch (IOException e) {
            throw new AssertionError(e);
        }
    }

    @SuppressWarnings("unchecked")
    private static void insertJsonDocument(MongoCollection collection, Map<String, Object> document) {
        try {
            String json = objectMapper.writeValueAsString(document);
            BasicDBObject dbObject = BasicDBObject.parse(json);
            collection.insertOne(new Document(dbObject.toMap()));
        } catch (JsonProcessingException ex) {
            throw new AssertionError(ex);
        }
    }

    private static MongoClient createMongoClient(ExtensionContext extensionContext) {
        ExtensionContext.Store store = extensionContext.getStore(NAMESPACE);
        GenericContainer container = store.get(CONTAINER_KEy, GenericContainer.class);

        int port = container.getFirstMappedPort();
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyToClusterSettings(builder -> builder.hosts(buildMongoDbHosts(port)))
                .applyToSslSettings(builder -> builder.enabled(false))
                .build();

        return MongoClients.create(settings);
    }

    private static class DataSet {
        private final String dbName;
        private final Map<String, List<Map<String, Object>>> documents;

        private DataSet(String name, Map<String, List<Map<String, Object>>> documents) {
            this.dbName = name;
            this.documents = documents;
        }
    }
}
