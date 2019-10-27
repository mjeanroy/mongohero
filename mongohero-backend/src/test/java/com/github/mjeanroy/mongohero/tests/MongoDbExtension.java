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

package com.github.mjeanroy.mongohero.tests;

import com.mongodb.MongoClientSettings;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.junit.jupiter.api.extension.*;
import org.junit.jupiter.api.extension.ExtensionContext.Namespace;
import org.junit.platform.commons.support.AnnotationSupport;
import org.testcontainers.containers.GenericContainer;

import java.util.Collections;
import java.util.List;

class MongoDbExtension implements BeforeAllCallback, AfterAllCallback, ParameterResolver {

    private static final Namespace NAMESPACE = Namespace.create(MongoDbExtension.class);
    private static final String CONTAINER_KEy = "MONGO_DB_CONTAINER";

    @Override
    public void beforeAll(ExtensionContext context) throws Exception {
        Class<?> testClass = context.getRequiredTestClass();
        MongoDbTest mongoDbTest = AnnotationSupport.findAnnotation(testClass, MongoDbTest.class).orElseThrow(() ->
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
        }
        finally {
            store.remove(CONTAINER_KEy);
        }
    }

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return MongoClient.class.isAssignableFrom(parameterContext.getParameter().getType());
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        ExtensionContext.Store store = extensionContext.getStore(NAMESPACE);
        GenericContainer container = store.get(CONTAINER_KEy, GenericContainer.class);
        int port = container.getFirstMappedPort();
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyToClusterSettings(builder -> builder.hosts(buildMongoDbHosts(port)))
                .applyToSslSettings(builder -> builder.enabled(false))
                .build();

        return MongoClients.create(settings);
    }

    private static List<ServerAddress> buildMongoDbHosts(int port) {
        return Collections.singletonList(buildMongoDbHost(port));
    }

    private static ServerAddress buildMongoDbHost(int port) {
        return new ServerAddress("localhost", port);
    }

}
