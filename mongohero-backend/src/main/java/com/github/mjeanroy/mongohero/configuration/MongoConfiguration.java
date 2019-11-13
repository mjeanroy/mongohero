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
import com.mongodb.ReadConcern;
import com.mongodb.ReadConcernLevel;
import com.mongodb.ReadPreference;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.connection.ClusterConnectionMode;
import com.mongodb.connection.ClusterSettings;
import com.mongodb.connection.ConnectionPoolSettings;
import com.mongodb.connection.SocketSettings;
import com.mongodb.connection.SslSettings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static java.lang.String.format;
import static java.util.Arrays.stream;
import static org.apache.commons.lang3.StringUtils.isEmpty;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;
import static org.apache.commons.lang3.StringUtils.trim;

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
				.applicationName("mongohero")
				.applyToClusterSettings(builder -> configureClusterSettings(mongoDbProperties, builder))
				.applyToSslSettings(builder -> configureSsl(mongoDbProperties, builder))
				.applyToConnectionPoolSettings(builder -> configureConnectionPool(mongoDbProperties, builder))
				.applyToSocketSettings(builder -> configureSocket(mongoDbProperties, builder));

		MongoDbOptions options = mongoDbProperties.getOptions();

		String readPreference = trim(options.getReadPreference());
		if (isNotEmpty(readPreference)) {
			log.debug("Configuring read preference to: {}", readPreference);
			settingsBuilder.readPreference(ReadPreference.valueOf(readPreference));
		}

		String readConcernLevel = trim(options.getReadConcernLevel());
		if (isNotEmpty(readConcernLevel)) {
			log.debug("Configuring readConcernLevel to: {}", readConcernLevel);
			settingsBuilder.readConcern(new ReadConcern(ReadConcernLevel.fromString(readConcernLevel)));
		}

		MongoCredential mongoCredentials = createMongoCredentials(mongoDbProperties);
		if (mongoCredentials != null) {
			log.debug("Configuring MongoDB credentials");
			settingsBuilder.credential(mongoCredentials);
		}

		return settingsBuilder.build();
	}

	private static void configureSocket(MongoDbProperties mongoDbProperties, SocketSettings.Builder builder) {
		MongoDbOptions options = mongoDbProperties.getOptions();

		int connectTimeoutMs = options.getConnectTimeoutMs();
		if (connectTimeoutMs > 0) {
			log.debug("Configuring connectTimeout to: {}ms", connectTimeoutMs);
			builder.connectTimeout(connectTimeoutMs, TimeUnit.MILLISECONDS);
		}

		int readTimeoutMs = options.getReadTimeoutMs();
		if (readTimeoutMs > 0) {
			log.debug("Configuring readTimeout to: {}ms", readTimeoutMs);
			builder.readTimeout(readTimeoutMs, TimeUnit.MILLISECONDS);
		}
	}

	private static void configureClusterSettings(MongoDbProperties mongoDbProperties, ClusterSettings.Builder builder) {
		builder.hosts(buildMongoDbHosts(mongoDbProperties));

		String replicaSetName = trim(mongoDbProperties.getReplicaSet());
		boolean isReplicaSetEnabled = isNotEmpty(replicaSetName);
		if (isReplicaSetEnabled) {
			log.debug("Configuring replica set name to: {}", replicaSetName);
			builder.requiredReplicaSetName(replicaSetName);
		}

		MongoDbOptions options = mongoDbProperties.getOptions();
		int maxWaitQueueSize = options.getMaxWaitQueueSize();
		if (maxWaitQueueSize >= 0) {
			log.debug("Configuring maxWaitQueueSize to: {}", maxWaitQueueSize);
			builder.maxWaitQueueSize(maxWaitQueueSize);
		}

		String connectionMode = trim(options.getConnectionMode());
		if (isEmpty(connectionMode) && isReplicaSetEnabled) {
			connectionMode = ClusterConnectionMode.MULTIPLE.name();
		}

		if (isNotEmpty(connectionMode)) {
			log.debug("Configuring connectionMode to: {}", connectionMode);
			builder.mode(parseClusterConnectionMode(connectionMode));
		}
	}

	private static void configureConnectionPool(MongoDbProperties mongoDbProperties, ConnectionPoolSettings.Builder builder) {
		MongoDbOptions options = mongoDbProperties.getOptions();

		int maxPoolSize = options.getMaxPoolSize();
		if (maxPoolSize >= 0) {
			log.debug("Configuring maxPoolSize to: {}", maxPoolSize);
			builder.maxSize(maxPoolSize);
		}

		int minPoolSize = options.getMinPoolSize();
		if (minPoolSize >= 0) {
			log.debug("Configuring minPoolSize to: {}", minPoolSize);
			builder.minSize(minPoolSize);
		}
	}

	private static void configureSsl(MongoDbProperties mongoDbProperties, SslSettings.Builder builder) {
		builder.enabled(mongoDbProperties.isSsl());
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
		log.debug("Building MongoDB ServerAddress from: {}", mongoDbHost);
		return new ServerAddress(mongoDbHost.getHost(), mongoDbHost.getPort());
	}

	private static ClusterConnectionMode parseClusterConnectionMode(String connectionMode) {
		if (connectionMode == null) {
			return null;
		}

		ClusterConnectionMode[] values = ClusterConnectionMode.values();
		for (ClusterConnectionMode clusterConnectionMode : values) {
			if (clusterConnectionMode.name().equalsIgnoreCase(connectionMode)) {
				return clusterConnectionMode;
			}
		}

		throw new IllegalArgumentException(format(
				"'%s' is not a valid connectionMode, allowed values are: %s",
				connectionMode,
				stream(values).map(ClusterConnectionMode::name).map(name -> "'" + name + "'").collect(Collectors.joining(" / ")))
		);
	}
}
