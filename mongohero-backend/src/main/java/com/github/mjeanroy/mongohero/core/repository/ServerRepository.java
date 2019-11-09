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

package com.github.mjeanroy.mongohero.core.repository;

import com.github.mjeanroy.mongohero.core.model.Operation;
import com.github.mjeanroy.mongohero.core.model.ReplicationStatus;
import com.github.mjeanroy.mongohero.core.model.Server;
import com.github.mjeanroy.mongohero.core.model.ServerLog;
import com.github.mjeanroy.mongohero.core.mongo.Mongo;
import com.github.mjeanroy.mongohero.core.mongo.MongoMapper;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

@Repository
public class ServerRepository {

	private final Mongo mongo;
	private final MongoMapper mongoMapper;

	@Autowired
	ServerRepository(Mongo mongoClient, MongoMapper mongoMapper) {
		this.mongo = mongoClient;
		this.mongoMapper = mongoMapper;
	}

	/**
	 * Get server information.
	 *
	 * @return Server information.
	 */
	public Server serverStatus() {
		Document document = mongo.serverStatus();
		return mongoMapper.map(document, Server.class);
	}

	/**
	 * Returns the most recent 1024 logged mongod events.
	 * This method does not read log data from the mongod log file.
	 * It instead reads data from a RAM cache of logged mongod events.
	 *
	 * @return The server log output.
	 * @see <a href="https://docs.mongodb.com/manual/reference/command/getLog/#dbcmd.getLog">https://docs.mongodb.com/manual/reference/command/getLog/#dbcmd.getLog</a>
	 */
	public ServerLog getLog() {
		Document document = mongo.getLog();
		return mongoMapper.map(document, ServerLog.class);
	}

	/**
	 * Get all configuration parameters.
	 *
	 * @return Configuration parameters.
	 * @see <a href="https://docs.mongodb.com/manual/reference/command/getParameter/#dbcmd.getParameter">https://docs.mongodb.com/manual/reference/command/getParameter/#dbcmd.getParameter</a>
	 */
	public Map<String, Object> getParameters() {
		return mongo.getParameter();
	}

	/**
	 * Get current operations in progress.
	 *
	 * @return Operation in progress.
	 */
	public Stream<Operation> getCurrentOp() {
		Document document = mongo.currentOp();
		Iterable<Document> documents = document.getList("inprog", Document.class);
		return mongoMapper.mapToStream(documents, Operation.class);
	}

	/**
	 * The replSetGetStatus command returns the status of the replica set from the point of view of
	 * the server that processed the command. replSetGetStatus must be run against the admin database.
	 *
	 * The mongod instance must be a replica set member for replSetGetStatus to return successfully.
	 *
	 * Data provided by this command derives from data included in heartbeats sent to the server
	 * by other members of the replica set.
	 * Because of the frequency of heartbeats, these data can be several seconds out of date.
	 *
	 * @return The replication status, it it exists.
	 */
	public Optional<ReplicationStatus> replSetGetStatus() {
		return mongo.replSetGetStatus().map(doc -> mongoMapper.map(doc, ReplicationStatus.class));
	}
}
