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

import com.mongodb.MongoCommandException;
import com.mongodb.ReadPreference;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * A proxy for {@link MongoClient} with missing features.
 */
@Component
public class Mongo {

	private static final Logger log = LoggerFactory.getLogger(Mongo.class);

	/**
	 * The internal mongo driver.
	 */
	private final MongoClient mongoClient;

	@Autowired
	public Mongo(MongoClient mongoClient) {
		this.mongoClient = mongoClient;
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
		return mongoClient.getDatabase("admin");
	}
}
