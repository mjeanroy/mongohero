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

package com.github.mjeanroy.mongohero.configuration;

import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.stream.Collectors;

@Configuration
class MongoConfiguration {

	private static final Logger log = LoggerFactory.getLogger(MongoConfiguration.class);

	@Bean
	MongoClient mongoClient(MongoDbProperties mongoDbProperties) {
		log.info("Configuring MongoDB Client using properties: {}", mongoDbProperties);
		return MongoClients.create(
				createMongoSettings(mongoDbProperties)
		);
	}

	private static MongoClientSettings createMongoSettings(MongoDbProperties mongoDbProperties) {
		MongoClientSettings.Builder settingsBuilder = MongoClientSettings.builder()
				.applyToClusterSettings(builder -> builder.hosts(buildMongoDbHosts(mongoDbProperties)))
				.applyToSslSettings(builder -> builder.enabled(mongoDbProperties.isSsl()))
				.applyToConnectionPoolSettings(builder -> {
					builder.maxSize(mongoDbProperties.getOptions().getMaxPoolSize());
					builder.minSize(mongoDbProperties.getOptions().getMinPoolSize());
				});

		MongoCredential mongoCredentials = createMongoCredentials(mongoDbProperties);
		if (mongoCredentials != null) {
			settingsBuilder.credential(mongoCredentials);
		}

		return settingsBuilder.build();
	}

	private static MongoCredential createMongoCredentials(MongoDbProperties mongoDbProperties) {
		String user = mongoDbProperties.getUser();
		if (user == null || user.isEmpty()) {
			return null;
		}

		return MongoCredential.createCredential(user, mongoDbProperties.getDatabase(), mongoDbProperties.getPassword().toCharArray());
	}

	private static List<ServerAddress> buildMongoDbHosts(MongoDbProperties mongoDbProperties) {
		return mongoDbProperties.getHosts().stream().map(MongoConfiguration::buildMongoDbHost).collect(Collectors.toList());
	}

	private static ServerAddress buildMongoDbHost(MongoDbHost mongoDbHost) {
		log.info("Building MongoDB ServerAddress from: {}", mongoDbHost);
		return new ServerAddress(mongoDbHost.getHost(), mongoDbHost.getPort());
	}
}
