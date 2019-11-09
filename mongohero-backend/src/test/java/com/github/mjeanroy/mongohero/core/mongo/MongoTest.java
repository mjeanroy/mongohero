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

import com.github.mjeanroy.mongohero.tests.MongoDb32Test;
import com.github.mjeanroy.mongohero.tests.MongoDb36Test;
import com.github.mjeanroy.mongohero.tests.MongoDb40Test;
import com.github.mjeanroy.mongohero.tests.MongoDb42Test;
import com.mongodb.client.MongoClient;
import org.bson.Document;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class MongoTest {

	@Nested
	@MongoDb32Test
	class Mongo32 {

		private Mongo mongo;

		@BeforeEach
		void setUp(MongoClient mongoClient) {
			mongo = new Mongo(mongoClient);
		}

		@Test
		void it_should_get_server_status() {
			Document document = mongo.serverStatus();
			verifyServerStatus(document, "3.2.16");
		}

		@Test
		void it_should_get_logs() {
			Document document = mongo.getLog();
			verifyGetLog(document);
		}

		@Test
		void it_should_get_parameter() {
			Document document = mongo.getParameter();
			verifyGetParameter(document);
		}

		@Test
		void it_should_get_empty_replication_status_on_non_replica_server() {
			Optional<Document> maybeDocument = mongo.replSetGetStatus();
			assertThat(maybeDocument).isEmpty();
		}

		@Test
		void it_should_get_current_op() {
			Document document = mongo.currentOp();
			verifyCurrentOp(document);
		}
	}

	@Nested
	@MongoDb36Test
	class Mongo36 {

		private Mongo mongo;

		@BeforeEach
		void setUp(MongoClient mongoClient) {
			mongo = new Mongo(mongoClient);
		}

		@Test
		void it_should_get_server_status() {
			Document document = mongo.serverStatus();
			verifyServerStatus(document, "3.6.15");
		}

		@Test
		void it_should_get_logs() {
			Document document = mongo.getLog();
			verifyGetLog(document);
		}

		@Test
		void it_should_get_parameter() {
			Document document = mongo.getParameter();
			verifyGetParameter(document);
		}

		@Test
		void it_should_get_current_op() {
			Document document = mongo.currentOp();
			verifyCurrentOp(document);
		}

		@Test
		void it_should_get_empty_replication_status_on_non_replica_server() {
			Optional<Document> maybeDocument = mongo.replSetGetStatus();
			assertThat(maybeDocument).isEmpty();
		}
	}

	@Nested
	@MongoDb40Test
	class Mongo40 {

		private Mongo mongo;

		@BeforeEach
		void setUp(MongoClient mongoClient) {
			mongo = new Mongo(mongoClient);
		}

		@Test
		void it_should_get_server_status() {
			Document document = mongo.serverStatus();
			verifyServerStatus(document, "4.0.13");
		}

		@Test
		void it_should_get_logs() {
			Document document = mongo.getLog();
			verifyGetLog(document);
		}

		@Test
		void it_should_get_parameter() {
			Document document = mongo.getParameter();
			verifyGetParameter(document);
		}

		@Test
		void it_should_get_empty_replication_status_on_non_replica_server() {
			Optional<Document> maybeDocument = mongo.replSetGetStatus();
			assertThat(maybeDocument).isEmpty();
		}

		@Test
		void it_should_get_current_op() {
			Document document = mongo.currentOp();
			verifyCurrentOp(document);
		}
	}

	@Nested
	@MongoDb42Test
	class Mongo42 {

		private Mongo mongo;

		@BeforeEach
		void setUp(MongoClient mongoClient) {
			mongo = new Mongo(mongoClient);
		}

		@Test
		void it_should_get_server_status() {
			Document document = mongo.serverStatus();
			verifyServerStatus(document, "4.2.1");
		}

		@Test
		void it_should_get_logs() {
			Document document = mongo.getLog();
			verifyGetLog(document);
		}

		@Test
		void it_should_get_parameter() {
			Document document = mongo.getParameter();
			verifyGetParameter(document);
		}

		@Test
		void it_should_get_empty_replication_status_on_non_replica_server() {
			Optional<Document> maybeDocument = mongo.replSetGetStatus();
			assertThat(maybeDocument).isEmpty();
		}

		@Test
		void it_should_get_current_op() {
			Document document = mongo.currentOp();
			verifyCurrentOp(document);
		}
	}

	private static void verifyServerStatus(Document document, String expectedVersion) {
		assertThat(document).isNotNull();
		assertThat(document.get("host")).isInstanceOf(String.class);
		assertThat(document.get("version")).isEqualTo(expectedVersion);
		assertThat(document.get("storageEngine")).isInstanceOf(Document.class);

		Document storageEngine = (Document) document.get("storageEngine");
		assertThat(storageEngine.get("name")).isEqualTo("wiredTiger");
		assertThat(storageEngine.get("persistent")).isEqualTo(true);
		assertThat(storageEngine.get("supportsCommittedReads")).isEqualTo(true);
	}

	@SuppressWarnings("unchecked")
	private static void verifyGetLog(Document document) {
		assertThat(document).isNotNull().containsKeys("log", "totalLinesWritten", "ok");
		assertThat((List<String>) document.get("log")).isNotEmpty();
	}

	private static void verifyGetParameter(Document document) {
		assertThat(document).isNotNull().isNotEmpty();
	}

	private static void verifyCurrentOp(Document document) {
		assertThat(document).isNotNull().containsKeys("inprog", "ok");
	}
}
