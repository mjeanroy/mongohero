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

package com.github.mjeanroy.mongohero.core.mongo;

import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.ReadConcern;
import com.mongodb.ReadConcernLevel;
import com.mongodb.ReadPreference;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.connection.ClusterConnectionMode;
import com.mongodb.connection.ClusterDescription;
import com.mongodb.connection.ClusterSettings;
import com.mongodb.connection.ConnectionPoolSettings;
import com.mongodb.connection.ServerConnectionState;
import com.mongodb.connection.ServerDescription;
import com.mongodb.connection.SocketSettings;
import com.mongodb.connection.SslSettings;
import com.mongodb.event.ClusterDescriptionChangedEvent;
import com.mongodb.event.ClusterListenerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.lang.String.format;
import static java.util.Arrays.stream;
import static java.util.Collections.singletonMap;
import static java.util.Collections.unmodifiableMap;
import static org.apache.commons.lang3.StringUtils.isEmpty;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;
import static org.apache.commons.lang3.StringUtils.trim;

@Component
public class MongoClientFactory {

	private static final Logger log = LoggerFactory.getLogger(MongoClientFactory.class);

	/**
	 * The MongoDB Properties.
	 */
	private final MongoDbProperties mongoDbProperties;

	/**
	 * The default mongo client, built using application configuration.
	 */
	private final MongoClient mongoClient;

	/**
	 * List of cluster member clients.
	 */
	private final Map<String, MongoClient> clusterClients;

	@Autowired
	public MongoClientFactory(MongoDbProperties mongoDbProperties) {
		this.mongoDbProperties = mongoDbProperties;
		this.mongoClient = buildMongoClient(mongoDbProperties, true);
		this.clusterClients = new ConcurrentHashMap<>();
		this.synchronizeClusterClients();
	}

	@PreDestroy
	void onDestroy() {
		closeMongoClient();
		closeClusterClients();
	}

	private void closeMongoClient() {
		mongoClient.close();
	}

	private void closeClusterClients() {
		clusterClients.values().forEach(
				MongoClient::close
		);
	}

	/**
	 * Get {@link #mongoClient}
	 *
	 * @return {@link #mongoClient}
	 */
	MongoClient getDefaultClient() {
		return mongoClient;
	}

	/**
	 * Get all known (connected) cluster clients.
	 *
	 * @return Mongo Clients.
	 */
	Map<String, MongoClient> getClusterClients() {
		if (clusterClients.isEmpty()) {
			return singletonMap(
					extractMongoClientHost(mongoClient), mongoClient
			);
		}

		return unmodifiableMap(
				clusterClients
		);
	}

	/**
	 * Get one client per cluster member.
	 *
	 * Note that each client <strong>must be closed manually</strong> after being used.
	 */
	private void synchronizeClusterClients() {
		if (mongoClient != null) {
			log.info("Synchronize mongo cluster clients");

			ClusterDescription clusterDescription = mongoClient.getClusterDescription();
			List<ServerDescription> servers = clusterDescription.getServerDescriptions();

			Map<String, ServerDescription> connectedServers = servers.stream()
					.filter(serverDescription -> serverDescription.getState() == ServerConnectionState.CONNECTED)
					.collect(Collectors.toMap(
							MongoClientFactory::extractRawServerAddress,
							Function.identity()
					));

			// Remove outdated clients
			for (String rawAddress : clusterClients.keySet()) {
				if (!connectedServers.containsKey(rawAddress)) {
					log.info("Closing mongo cluster client: {}", rawAddress);
					clusterClients.get(rawAddress).close();
					clusterClients.remove(rawAddress);
				}
			}

			// Add new clients if we found some
			if (!servers.isEmpty()) {
				// Try to adjust max pool size at best across all clients.
				MongoDbOptions options = mongoDbProperties.getOptions();
				int maxPoolSizeFromOptions = options.getMaxPoolSize();
				int maxPoolSizeRepartition = maxPoolSizeFromOptions == 0 ? 0 : maxPoolSizeFromOptions / servers.size();
				int maxPoolSize = Math.max(1, maxPoolSizeRepartition);

				for (ServerDescription serverDescription : connectedServers.values()) {
					String rawAddress = extractRawServerAddress(serverDescription);
					if (!clusterClients.containsKey(rawAddress)) {
						log.info("Add new mongo cluster client: {}", rawAddress);
						this.clusterClients.put(rawAddress, createClusterClient(serverDescription, maxPoolSize));
					}
				}
			}
		}
	}

	/**
	 * Create Mongo Client from given Server Description.
	 *
	 * @param serverDescription Server description.
	 * @param maxPoolSize Maximum Pool Size.
	 * @return The Mongo Client.
	 */
	private MongoClient createClusterClient(ServerDescription serverDescription, int maxPoolSize) {
		MongoDbOptions options = mongoDbProperties.getOptions();
		int minPoolSize = Math.min(options.getMinPoolSize(), maxPoolSize);
		MongoDbProperties serverProperties = mongoDbProperties.toBuilder()
				.withHost(serverDescription.getAddress())
				.withReplicaSet(null)
				.withOptions(options.toBuilder()
						.withConnectionMode(ClusterConnectionMode.SINGLE.name())
						.withMaxPoolSize(maxPoolSize)
						.withMinPoolSize(minPoolSize)
						.build())
				.build();

		return buildMongoClient(serverProperties, false);
	}

	/**
	 * Build MongoDB Client from given properties.
	 *
	 * @param mongoDbProperties MongoDB Properties.
	 * @param useClusterListener Set to {@code true} to listen to cluster change events, and re-synchronize internal cluster clients.
	 * @return Mongo Client.
	 */
	private MongoClient buildMongoClient(MongoDbProperties mongoDbProperties, boolean useClusterListener) {
		log.info("Configuring MongoDB Client using properties: {}", mongoDbProperties);
		return MongoClients.create(
				createMongoSettings(mongoDbProperties, useClusterListener)
		);
	}

	/**
	 * Build MongoDB Settings from given properties.
	 *
	 * @param mongoDbProperties MongoDB Properties.
	 * @param useClusterListener Set to {@code true} to listen to cluster change events, and re-synchronize internal cluster clients.
	 * @return Mongo Settings.
	 */
	private MongoClientSettings createMongoSettings(MongoDbProperties mongoDbProperties, boolean useClusterListener) {
		MongoClientSettings.Builder settingsBuilder = MongoClientSettings.builder()
				.applicationName("mongohero")
				.applyToClusterSettings(builder -> configureClusterSettings(mongoDbProperties, builder, useClusterListener))
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

	/**
	 * Configure Socket Properties from given properties::
	 *
	 * <ul>
	 *   <li>Set Connection Timeout from {@link MongoDbOptions#getConnectTimeoutMs()}</li>
	 *   <li>Set Read Timeout from {@link MongoDbOptions#getReadTimeoutMs()}</li>
	 * </ul>
	 *
	 * @param mongoDbProperties MongoDB Properties.
	 * @param builder Mongo Client configuration builder.
	 */
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

	/**
	 * Configure Cluster Properties from given properties::
	 *
	 * <ul>
	 *   <li>Set Replica Set Name from {@link MongoDbProperties#getReplicaSet()}</li>
	 *   <li>Set Max Wait Queue Size from {@link MongoDbOptions#getMaxWaitQueueSize()}</li>
	 *   <li>Set Connection Mode from {@link MongoDbOptions#getConnectionMode()} or automatically according to replica set setting.</li>
	 * </ul>
	 *
	 * @param mongoDbProperties MongoDB Properties.
	 * @param builder Mongo Client configuration builder.
	 * @param useClusterListener Set to {@code true} to listen to cluster change events, and re-synchronize internal cluster clients.
	 */
	private void configureClusterSettings(MongoDbProperties mongoDbProperties, ClusterSettings.Builder builder, boolean useClusterListener) {
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

		if (useClusterListener) {
			builder.addClusterListener(new ClusterListenerAdapter() {
				@Override
				public void clusterDescriptionChanged(ClusterDescriptionChangedEvent event) {
					MongoClientFactory.this.synchronizeClusterClients();
				}
			});
		}
	}

	/**
	 * Configure Connection Pool from given properties::
	 *
	 * <ul>
	 *   <li>Set Max Pool Size from {@link MongoDbOptions#getMaxPoolSize()}</li>
	 *   <li>Set Min Pool Size from {@link MongoDbOptions#getMinPoolSize()}</li>
	 * </ul>
	 *
	 * @param mongoDbProperties MongoDB Properties.
	 * @param builder Mongo Client configuration builder.
	 */
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

	/**
	 * Enable/Disable SSL Mode according to {@link MongoDbProperties#isSsl()} flag.
	 *
	 * @param mongoDbProperties MongoDB Properties.
	 * @param builder Mongo Client configuration builder.
	 */
	private static void configureSsl(MongoDbProperties mongoDbProperties, SslSettings.Builder builder) {
		builder.enabled(mongoDbProperties.isSsl());
	}

	/**
	 * Create Mongo Credentials from given properties, this method may return {@code null}
	 * if {@link MongoDbProperties#getUser()} is not defined.
	 *
	 * @param mongoDbProperties MongoDB Properties.
	 * @return Mongo Credentials.
	 */
	private static MongoCredential createMongoCredentials(MongoDbProperties mongoDbProperties) {
		String user = mongoDbProperties.getUser();
		if (user == null || user.isEmpty()) {
			return null;
		}

		return MongoCredential.createCredential(user, mongoDbProperties.getDatabase(), mongoDbProperties.getPassword().toCharArray());
	}

	/**
	 * Create MongoDB Hosts from given properties: extract and parse comma separated list specified
	 * in {@link MongoDbProperties#getHost()} and build associated {@link ServerAddress}.
	 *
	 * @param mongoDbProperties MongoDB Properties.
	 * @return List of MongoDB server addresses.
	 */
	private static List<ServerAddress> buildMongoDbHosts(MongoDbProperties mongoDbProperties) {
		return mongoDbProperties.getHosts().stream().map(MongoClientFactory::buildMongoDbHost).collect(Collectors.toList());
	}

	/**
	 * Build MongoDB Host from given host properties.
	 *
	 * @param mongoDbHost Host Properties.
	 * @return MongoDB Server Address.
	 */
	private static ServerAddress buildMongoDbHost(MongoDbHost mongoDbHost) {
		log.debug("Building MongoDB ServerAddress from: {}", mongoDbHost);
		return new ServerAddress(mongoDbHost.getHost(), mongoDbHost.getPort());
	}

	/**
	 * Parse connection mode setting to given {@link ClusterConnectionMode}: this method is case insensitive
	 * and may returns {@code null} if {@code connectionMode} is {@code null}.
	 *
	 * If {@code connectionMode} is not a valid value, this method will fail with an {@link IllegalArgumentException}.
	 *
	 * @param connectionMode Connection Mode value.
	 * @return The MongoDB {@link ClusterConnectionMode}.
	 */
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

	/**
	 * Extract host address targeted by this {@link MongoClient}.
	 * If this client is connected to multiple servers, the first known will be returned.
	 *
	 * @param mongoClient Mongo Client.
	 * @return Target Host.
	 */
	private static String extractMongoClientHost(MongoClient mongoClient) {
		ServerAddress serverAddress = extractMongoClientServerAddress(mongoClient);
		return extractRawServerAddress(serverAddress);
	}

	/**
	 * Extract server address targeted by this {@link MongoClient}.
	 * If this client is connected to multiple servers, the first known will be returned.
	 *
	 * @param mongoClient Mongo Client.
	 * @return Target Server.
	 */
	private static ServerAddress extractMongoClientServerAddress(MongoClient mongoClient) {
		ClusterDescription clusterDescription = mongoClient.getClusterDescription();
		List<ServerDescription> serverDescriptions = clusterDescription.getServerDescriptions();
		ServerDescription serverDescription = serverDescriptions.get(0);
		return serverDescription.getAddress();
	}

	/**
	 * Extract server raw address, such as [host]:[port] from given {@link ServerDescription}.
	 *
	 * @param serverDescription The server description.
	 * @return The raw address.
	 */
	private static String extractRawServerAddress(ServerDescription serverDescription) {
		ServerAddress serverAddress = serverDescription.getAddress();
		return serverAddress.getHost() + ":" + serverAddress.getPort();
	}

	/**
	 * Extract server raw address, such as [host]:[port] from given {@link ServerAddress}.
	 *
	 * @param serverAddress The server address.
	 * @return The raw address.
	 */
	private static String extractRawServerAddress(ServerAddress serverAddress) {
		return serverAddress.getHost() + ":" + serverAddress.getPort();
	}
}
