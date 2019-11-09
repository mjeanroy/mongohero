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
import com.mongodb.ReadConcern;
import com.mongodb.WriteConcern;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings("InnerClassMayBeStatic")
class MongoTest {

	private static abstract class AbstractMongoTest {

		private Mongo mongo;

		@BeforeEach
		void setUp(MongoClient mongoClient) {
			mongo = new Mongo(mongoClient);
		}

		@Test
		void it_should_get_database() {
			Optional<MongoDatabase> maybeDatase = mongo.getDatabase("movies");
			assertThat(maybeDatase).isPresent().hasValueSatisfying(db -> {
				assertThat(db.getName()).isEqualTo("movies");
				assertThat(db.getReadConcern()).isEqualTo(ReadConcern.DEFAULT);
				assertThat(db.getWriteConcern()).isEqualTo(WriteConcern.ACKNOWLEDGED);
			});
		}

		@Test
		void it_should_list_database_collections() {
			List<Document> collections = mongo.listCollections("marvels").collect(Collectors.toList());
			assertThat(collections).hasSize(2);
			assertThat(collections.stream().map(doc -> doc.get("name"))).hasSize(2).contains("movies", "avengers");
		}

		@Test
		void it_should_get_server_status() {
			Document document = mongo.serverStatus();

			assertThat(document).isNotNull();
			assertThat(document.get("host")).isInstanceOf(String.class);
			assertThat(document.get("version")).isEqualTo(expectedVersion());
			assertThat(document.get("storageEngine")).isInstanceOf(Document.class);

			Document storageEngine = (Document) document.get("storageEngine");
			assertThat(storageEngine.get("name")).isEqualTo("wiredTiger");
			assertThat(storageEngine.get("persistent")).isEqualTo(true);
			assertThat(storageEngine.get("supportsCommittedReads")).isEqualTo(true);
		}

		@Test
		@SuppressWarnings("unchecked")
		void it_should_get_logs() {
			Document document = mongo.getLog();
			assertThat(document).isNotNull().containsKeys("log", "totalLinesWritten", "ok");
			assertThat((List<String>) document.get("log")).isNotEmpty();
		}

		@Test
		void it_should_get_parameter() {
			Document document = mongo.getParameter();
			assertThat(document).isNotNull().isNotEmpty();
		}

		@Test
		void it_should_get_empty_replication_status_on_non_replica_server() {
			Optional<Document> maybeDocument = mongo.replSetGetStatus();
			assertThat(maybeDocument).isEmpty();
		}

		@Test
		void it_should_get_current_op() {
			Document document = mongo.currentOp();
			assertThat(document).isNotNull().containsKeys("inprog", "ok");
		}

		abstract String expectedVersion();
	}

	@Nested
	@MongoDb32Test
	class Mongo32 extends AbstractMongoTest {

		@Override
		String expectedVersion() {
			return "3.2.16";
		}
	}

	@Nested
	@MongoDb36Test
	class Mongo36 extends AbstractMongoTest {

		@Override
		String expectedVersion() {
			return "3.6.15";
		}
	}

	@Nested
	@MongoDb40Test
	class Mongo40 extends AbstractMongoTest {

		@Override
		String expectedVersion() {
			return "4.0.13";
		}
	}

	@Nested
	@MongoDb42Test
	class Mongo42 extends AbstractMongoTest {

		@Override
		String expectedVersion() {
			return "4.2.1";
		}
	}
}
