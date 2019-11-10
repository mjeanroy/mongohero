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
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SuppressWarnings("InnerClassMayBeStatic")
class MongoTest {

	private static abstract class AbstractMongoTest {

		private Mongo mongo;

		@BeforeEach
		void setUp(MongoClient mongoClient) {
			mongo = new Mongo(mongoClient);
		}

		@Test
		void it_should_list_database_collections() {
			List<Document> collections = mongo.listCollections("marvels").collect(Collectors.toList());
			assertThat(collections).hasSize(2);
			assertThat(collections.stream().map(doc -> doc.get("name"))).hasSize(2).contains("movies", "avengers");
		}

		@Test
		void it_should_fail_to_list_admin_database_collections() {
			assertThatThrownBy(() -> mongo.listCollections("admin")).isInstanceOf(IllegalMongoDatabaseAccessException.class);
		}

		@Test
		void it_should_fail_to_list_local_database_collections() {
			assertThatThrownBy(() -> mongo.listCollections("local")).isInstanceOf(IllegalMongoDatabaseAccessException.class);
		}

		@Test
		void it_should_get_db() {
			Optional<Document> maybeDocument = mongo.database("marvels");
			assertThat(maybeDocument).isPresent().hasValueSatisfying(document -> {
				assertThat(document.get("name")).isEqualTo("marvels");
				assertThat(document.get("empty")).isEqualTo(false);
				assertThat((double) document.get("sizeOnDisk")).isGreaterThan(0);
			});
		}

		@Test
		void it_should_returns_empty_with_unknown_db() {
			Optional<Document> maybeDocument = mongo.database("foo_bar");
			assertThat(maybeDocument).isEmpty();
		}

		@Test
		void it_should_fail_to_get_admin_db() {
			assertThatThrownBy(() -> mongo.database("admin")).isInstanceOf(IllegalMongoDatabaseAccessException.class);
		}

		@Test
		void it_should_fail_to_get_local_db() {
			assertThatThrownBy(() -> mongo.database("local")).isInstanceOf(IllegalMongoDatabaseAccessException.class);
		}

		@Test
		void it_should_get_db_stats() {
			Document document = mongo.dbstats("marvels");
			assertThat(document.get("db")).isEqualTo("marvels");
			assertThat((int) document.get("collections")).isEqualTo(2);
			assertThat((int) document.get("objects")).isEqualTo(7);
			assertThat((double) document.get("avgObjSize")).isGreaterThan(0);
			assertThat((double) document.get("dataSize")).isGreaterThan(0);
			assertThat((double) document.get("storageSize")).isGreaterThan(0);
		}

		@Test
		void it_should_fail_to_get_admin_db_stats() {
			assertThatThrownBy(() -> mongo.dbstats("admin")).isInstanceOf(IllegalMongoDatabaseAccessException.class);
		}

		@Test
		void it_should_fail_to_get_local_db_stats() {
			assertThatThrownBy(() -> mongo.dbstats("local")).isInstanceOf(IllegalMongoDatabaseAccessException.class);
		}

		@Test
		void it_should_get_collection_stats() {
			Document document = mongo.collStats("marvels", "avengers");
			assertThat(document).isNotNull();
			assertThat(document.get("ns")).isEqualTo("marvels.avengers");
			assertThat(document.get("count")).isEqualTo(3);
			assertThat((int) document.get("storageSize")).isGreaterThan(0);
			assertThat((int) document.get("avgObjSize")).isGreaterThan(0);
			assertThat((int) document.get("nindexes")).isEqualTo(1);
			assertThat((int) document.get("totalIndexSize")).isGreaterThan(0);
		}

		@Test
		void it_should_fail_to_get_admin_database_collection_stats() {
			assertThatThrownBy(() -> mongo.collStats("admin", "test")).isInstanceOf(IllegalMongoDatabaseAccessException.class);
		}

		@Test
		void it_should_fail_to_get_local_database_collection_stats() {
			assertThatThrownBy(() -> mongo.collStats("local", "test")).isInstanceOf(IllegalMongoDatabaseAccessException.class);
		}

		@Test
		void it_should_fail_to_get_stats_on_system_profiles_collection() {
			assertThatThrownBy(() -> mongo.collStats("marvels", "system.profile")).isInstanceOf(IllegalMongoCollectionAccessException.class);
		}

		@Test
		void it_should_get_index_stats() {
			List<Document> documents = mongo.indexStats("marvels", "avengers").collect(Collectors.toList());
			assertThat(documents).hasSize(1);

			Document document = documents.get(0);
			assertThat(document.get("name")).isEqualTo("_id_");
			assertThat(document.get("host")).isNotNull();
			assertThat(document.get("key")).isInstanceOf(Document.class);
			assertThat((Document) document.get("key")).hasSize(1).containsEntry("_id", 1);
			assertThat(document.get("accesses")).isInstanceOf(Document.class);
			assertThat((Document) document.get("accesses")).hasSize(2).containsKeys("ops", "since");
		}

		@Test
		void it_should_fail_to_get_index_stats_on_admin_database() {
			assertThatThrownBy(() -> mongo.indexStats("admin", "avengers")).isInstanceOf(IllegalMongoDatabaseAccessException.class);
		}

		@Test
		void it_should_fail_to_get_index_stats_on_local_database() {
			assertThatThrownBy(() -> mongo.indexStats("local", "avengers")).isInstanceOf(IllegalMongoDatabaseAccessException.class);
		}

		@Test
		void it_should_fail_to_get_index_stats_on_system_profile_collection() {
			assertThatThrownBy(() -> mongo.indexStats("marvels", "system.profile")).isInstanceOf(IllegalMongoCollectionAccessException.class);
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

		@Test
		void it_should_get_profiling_level() {
			Document document = mongo.getProfilingLevel("marvels");
			assertThat(document).isNotNull();
			assertThat(document.get("was")).isEqualTo(0);
			assertThat(document.get("slowms")).isEqualTo(100);
		}

		@Test
		void it_should_set_profiling_level() {
			mongo.setProfilingLevel("marvels", 1, 50);

			Document document = mongo.getProfilingLevel("marvels");
			assertThat(document).isNotNull();
			assertThat(document.get("was")).isEqualTo(1);
			assertThat(document.get("slowms")).isEqualTo(50);
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
