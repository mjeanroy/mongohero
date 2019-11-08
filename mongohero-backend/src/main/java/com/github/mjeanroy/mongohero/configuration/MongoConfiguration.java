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

package com.github.mjeanroy.mongohero.configuration;

import com.github.mjeanroy.mongohero.mongo.MongoMapper;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;
import java.util.List;

@Configuration
class MongoConfiguration {

	@Bean
	MongoClient mongoClient(MongoDbProperties mongoDbProperties) {
		return MongoClients.create(
				createMongoSettings(mongoDbProperties)
		);
	}

	@Bean
	MongoMapper mongoMapper() {
		return new MongoMapper();
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
		return Collections.singletonList(buildMongoDbHost(mongoDbProperties));
	}

	private static ServerAddress buildMongoDbHost(MongoDbProperties mongoDbProperties) {
		return new ServerAddress(mongoDbProperties.getHost(), mongoDbProperties.getPort());
	}
}
