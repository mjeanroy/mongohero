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

import com.github.mjeanroy.mongohero.commons.Streams;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoCommandException;
import com.mongodb.ReadPreference;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.connection.ClusterDescription;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import static com.github.mjeanroy.mongohero.commons.Streams.toStream;
import static com.github.mjeanroy.mongohero.core.mongo.MongoPreConditions.checkCollectionNameValidity;
import static com.github.mjeanroy.mongohero.core.mongo.MongoPreConditions.checkDatabaseNameValidity;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyMap;
import static java.util.Collections.singleton;
import static java.util.Collections.singletonList;

/**
 * A proxy for {@link MongoClient} with missing features.
 */
@Component
public class Mongo {

	private static final String SYSTEM_PROFILE_COLLECTION_NAME = "system.profile";

	private static final Set<String> BLACKLIST_DB = new HashSet<>(asList(
			"admin",
			"local"
	));

	private static final Set<String> BLACKLIST_COLLECTION = singleton(
			SYSTEM_PROFILE_COLLECTION_NAME
	);

	private static final Logger log = LoggerFactory.getLogger(Mongo.class);

	/**
	 * The factory that can be used to retrieved mongo client..
	 */
	private final MongoClientFactory mongoClientFactory;

	@Autowired
	public Mongo(MongoClientFactory mongoClientFactory) {
		this.mongoClientFactory = mongoClientFactory;
	}

	/**
	 * Gets the <strong>current</strong> cluster description.
	 *
	 * @return The <strong>current</strong> cluster description
	 */
	public ClusterDescription clusterDescription() {
		log.info("Getting cluster description");
		return mongoClient().getClusterDescription();
	}

	/**
	 * Gets the list of databases
	 *
	 * @return The list databases.
	 */
	public Stream<Document> listDatabases() {
		log.info("Listing databases");
		return toStream(mongoClient().listDatabases()).filter(doc -> isNotBlackListedDatabase((String) doc.get("name")));
	}

	/**
	 * Get database if it exists (i.e the database document returned in {@link #listDatabases()}.
	 *
	 * @param databaseName Database name.
	 * @return The database document, if it exists.
	 */
	public Optional<Document> database(String databaseName) {
		checkDatabaseName(databaseName);

		log.info("Getting database: {}", databaseName);
		return toStream(mongoClient().listDatabases())
				.filter(doc -> Objects.equals(doc.get("name"), databaseName))
				.findFirst();
	}

	/**
	 * List all collections of given database.
	 *
	 * Note that system collections such as {@code "system.profile"} are excluded.
	 *
	 * @param databaseName THe database name.
	 * @return All collections.
	 */
	@SuppressWarnings("SuspiciousMethodCalls")
	public Stream<Document> listCollections(String databaseName) {
		checkDatabaseName(databaseName);

		log.info("Listing collections of database: {}", databaseName);
		MongoDatabase mongoDatabase = mongoClient().getDatabase(databaseName);
		return Streams.toStream(mongoDatabase.listCollections()).filter(document -> !BLACKLIST_COLLECTION.contains(document.get("name")));
	}

	public Document dbstats(String databaseName) {
		checkDatabaseName(databaseName);

		log.info("Get dbstats of: {}", databaseName);
		Document command = new Document("dbstats", 1);
		return runCommand(databaseName, command);
	}

	/**
	 * Execute {@code "collStats"} command on given database to get a variety of storage statistics for a given collection.
	 *
	 * @param databaseName   Given database name.
	 * @param collectionName Given collection name.
	 * @return The {@code "collStats"} command output.
	 * @see <a href="https://docs.mongodb.com/manual/reference/command/collStats/">https://docs.mongodb.com/manual/reference/command/collStats/</a>
	 */
	public Document collStats(String databaseName, String collectionName) {
		checkDatabaseName(databaseName);
		checkCollectionName(collectionName);

		log.info("Get collection stats of {} # {}", databaseName, collectionName);
		Document command = new Document("collStats", collectionName);
		return runCommand(databaseName, command);
	}

	/**
	 * Extract and read index statistics on given collection.
	 *
	 * @param databaseName   Given database name.
	 * @param collectionName Given collection name.
	 * @return The {@code "$indexStats"} command output.
	 * @see <a href="https://docs.mongodb.com/manual/reference/operator/aggregation/indexStats/">https://docs.mongodb.com/manual/reference/operator/aggregation/indexStats/</a>
	 */
	public Stream<Document> indexStats(String databaseName, String collectionName) {
		checkDatabaseName(databaseName);
		checkCollectionName(collectionName);

		log.info("Get index stats of {} # {}", databaseName, collectionName);

		MongoDatabase mongoDatabase = mongoClient().getDatabase(databaseName);
		MongoCollection<Document> mongoCollection = mongoDatabase.getCollection(collectionName);
		Iterable<Document> indexStats = mongoCollection.aggregate(singletonList(
				new Document("$indexStats", emptyMap())
		));

		return toStream(indexStats);
	}

	/**
	 * Run {@code "serverStatus"} command against the {@code "admin"} database.
	 *
	 * @return The command result.
	 * @see <a href="https://docs.mongodb.com/manual/reference/command/serverStatus/">https://docs.mongodb.com/manual/reference/command/serverStatus/</a>
	 */
	public Document serverStatus() {
		log.info("Getting server status");
		return runAdminCommand(new Document("serverStatus", 1));
	}

	/**
	 * Execute {@code "getLog"} against {@code "admin"} database, returns a document containing the most recent 1024 logged mongod events.
	 *
	 * @return THe {@code "getLog"} command output.
	 * @see <a href="https://docs.mongodb.com/manual/reference/command/getLog/">https://docs.mongodb.com/manual/reference/command/getLog/</a>
	 */
	public Document getLog() {
		log.info("Getting server log");
		return runAdminCommand(new Document("getLog", "global"));
	}

	/**
	 * Execute {@code "getParameter"} against {@code "admin"} database.
	 *
	 * @return The {@code "getParameter"} command output.
	 * @see <a href="https://docs.mongodb.com/manual/reference/command/getParameter/">https://docs.mongodb.com/manual/reference/command/getParameter/</a>
	 */
	public Document getParameter() {
		log.info("Getting server parameters");
		return runAdminCommand(new Document("getParameter", "*"));
	}

	/**
	 * Execute {@code "currentOp"} against {@code "admin"} database.
	 *
	 * @return The {@code "currentOp"} command output.
	 * @see <a href="https://docs.mongodb.com/manual/reference/method/db.currentOp/">https://docs.mongodb.com/manual/reference/method/db.currentOp/</a>
	 */
	public Document currentOp() {
		log.info("Getting mongo current op");
		return runAdminCommand(new Document("currentOp", "1"));
	}

	/**
	 * Execute {@code "replSetGetStatus"} against {@code "admin"} database and returns command output if replication is enabled
	 * on this server (otherwise returns empty result).
	 *
	 * @return The {@code "replSetGetStatus"} command output.
	 * @see <a href="https://docs.mongodb.com/manual/reference/command/replSetGetStatus/">https://docs.mongodb.com/manual/reference/command/replSetGetStatus/</a>
	 */
	public Optional<Document> replSetGetStatus() {
		try {
			log.info("Getting replication information for this server");
			return Optional.of(runAdminCommand(new Document("replSetGetStatus", "1")));
		}
		catch (MongoCommandException ex) {
			if (ex.getMessage().contains("--replSet")) {
				log.info("Replication is enabled on this server, returns empty result");
				return Optional.empty();
			}

			throw ex;
		}
	}

	/**
	 * Get profiling level for given database.
	 *
	 * @param databaseName Database name.
	 * @return The {@code "profile"} command output.
	 */
	public Document getProfilingLevel(String databaseName) {
		checkDatabaseName(databaseName);

		log.info("Get profiling level set on database: {}", databaseName);
		Document command = new Document("profile", -1);
		return runCommand(databaseName, command);
	}

	/**
	 * Set profiling level for given database.
	 *
	 * @param databaseName Database name.
	 * @param level        New profiling level.
	 * @param slowMs       New slow ms threshold.
	 */
	public void setProfilingLevel(String databaseName, int level, int slowMs) {
		checkDatabaseName(databaseName);

		log.info("Set profiling level set on database '{}' with level={} and slowms={}", databaseName, level, slowMs);
		Document command = new Document();
		command.put("profile", level);
		command.put("slowms", slowMs);

		runCommandOnAll(databaseName, command);
	}

	/**
	 * Count number of queries currently stored in {@code "system.profile"} collection.
	 *
	 * @param databaseName Database name.
	 * @param filters      Filters (optional).
	 * @return Number of queries currently stored in {@code "system.profile"} collection.
	 */
	public long countSystemProfile(String databaseName, BasicDBObject filters) {
		checkDatabaseName(databaseName);

		log.info("Count {} # system.profile (filters = {})", databaseName, filters);
		final MongoDatabase systemDb = mongoClient().getDatabase(databaseName);
		final MongoCollection<Document> collection = systemDb.getCollection(SYSTEM_PROFILE_COLLECTION_NAME);
		final BasicDBObject mongoFilters = filters == null ? new BasicDBObject() : filters;
		return collection.countDocuments(mongoFilters);
	}

	/**
	 * Get queries currently stored in {@code "system.profile"} collection.
	 *
	 * @param databaseName Database name.
	 * @param filters      Filters (optional).
	 * @param offset       The offset.
	 * @param limit        The limit (a.k.a the page size).
	 * @param sort         The sort to apply.
	 * @return Number of queries currently stored in {@code "system.profile"} collection.
	 */
	public Stream<Document> findSystemProfile(String databaseName, BasicDBObject filters, int offset, int limit, Document sort) {
		checkDatabaseName(databaseName);

		log.info("Get {} # system.profile (filters = {} ; offset={} ; limit={} ; sort = {})", databaseName, filters, offset, limit, sort);
		final MongoDatabase systemDb = mongoClient().getDatabase(databaseName);
		final MongoCollection<Document> collection = systemDb.getCollection(SYSTEM_PROFILE_COLLECTION_NAME);
		final BasicDBObject mongoFilters = filters == null ? new BasicDBObject() : filters;
		return toStream(collection.find(mongoFilters).sort(sort).skip(offset).limit(limit));
	}

	/**
	 * Drop {@code "system.profile"} collection on given database.
	 *
	 * @param databaseName Database name.
	 */
	public void dropSystemProfile(String databaseName) {
		checkDatabaseName(databaseName);

		log.info("Dropping {} # system.profile", databaseName);
		mongoClient().getDatabase(databaseName).getCollection(SYSTEM_PROFILE_COLLECTION_NAME).drop();
	}

	/**
	 * Executes the given command in the context of the {@code "admin"} database with a
	 * read preference of {@link ReadPreference#primary()}.
	 *
	 * @param databaseName The database name.
	 * @param command      the command to be run
	 * @return the command result
	 */
	private Document runCommand(String databaseName, Document command) {
		log.info("Executing (on database: {}) command: {}", databaseName, command);
		return mongoClient().getDatabase(databaseName).runCommand(command);
	}

	/**
	 * Executes the given command in the context of the {@code "admin"} database with a
	 * read preference of {@link ReadPreference#primary()}.
	 *
	 * @param databaseName The database name.
	 * @param command      the command to be run
	 */
	private void runCommandOnAll(String databaseName, Document command) {
		mongoClientFactory.getClusterClients().forEach(client ->
				runCommandAndCloseClient(client, databaseName, command)
		);
	}

	/**
	 * Executes the given command in the context of the given database with a
	 * read preference of {@link ReadPreference#primary()}.
	 *
	 * @param mongoClient  The client to use and close after command has been executed.
	 * @param databaseName The database name.
	 * @param command      the command to be run
	 */
	private void runCommandAndCloseClient(MongoClient mongoClient, String databaseName, Document command) {
		log.debug("Run command {} on given database {}", command, databaseName);

		try {
			mongoClient.getDatabase(databaseName).runCommand(command);
		}
		finally {
			mongoClient.close();
		}
	}

	/**
	 * Executes the given command in the context of the {@code "admin"} database with a
	 * read preference of {@link ReadPreference#primary()}.
	 *
	 * @param command the command to be run
	 * @return the command result
	 */
	private Document runAdminCommand(Document command) {
		log.info("Executing command: {}", command);
		return getAdminDatabase().runCommand(command);
	}

	/**
	 * Gets the {@code "admin"} {@link MongoDatabase} instance.
	 *
	 * @return The {@code "admin"} {@code MongoDatabase}.
	 */
	private MongoDatabase getAdminDatabase() {
		log.info("Getting 'admin' database");
		return mongoClient().getDatabase("admin");
	}

	/**
	 * Get the default mongo client.
	 *
	 * @return Mongo Client.
	 */
	private MongoClient mongoClient() {
		return mongoClientFactory.getDefaultClient();
	}

	/**
	 * Check for database name validity and accessibility.
	 *
	 * @param databaseName The database name.
	 */
	private static void checkDatabaseName(String databaseName) {
		checkDatabaseNameValidity(databaseName);

		if (isBlackListedDatabase(databaseName)) {
			throw new IllegalMongoDatabaseAccessException(databaseName);
		}
	}

	/**
	 * Check for collection name validity and accessibility.
	 *
	 * @param collectionName The collection name.
	 */
	private static void checkCollectionName(String collectionName) {
		checkCollectionNameValidity(collectionName);

		if (BLACKLIST_COLLECTION.contains(collectionName)) {
			throw new IllegalMongoCollectionAccessException(collectionName);
		}
	}

	/**
	 * Check if given database name is part of blacklisted databases.
	 *
	 * @param databaseName The database name.
	 * @return {@code true} if given database <strong>is</strong> blacklisted, {@code false} otherwise.
	 */
	private static boolean isBlackListedDatabase(String databaseName) {
		return BLACKLIST_DB.contains(databaseName.toLowerCase());
	}

	/**
	 * Check if given database name is <strong>not</strong> part of blacklisted databases.
	 *
	 * @param databaseName The database name.
	 * @return {@code true} if given database <strong>is not</strong> blacklisted, {@code false} otherwise.
	 */
	private static boolean isNotBlackListedDatabase(String databaseName) {
		return !isBlackListedDatabase(databaseName);
	}
}
