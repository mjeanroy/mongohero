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

package com.github.mjeanroy.mongohero.core.repository;

import com.github.mjeanroy.mongohero.core.model.Database;
import com.github.mjeanroy.mongohero.core.mongo.IllegalMongoDatabaseAccessException;
import com.github.mjeanroy.mongohero.core.mongo.Mongo;
import com.github.mjeanroy.mongohero.core.mongo.MongoMapper;
import com.github.mjeanroy.mongohero.tests.MongoDb32Test;
import com.mongodb.client.MongoClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.tuple;

@MongoDb32Test
class DatabaseRepositoryTest {

	private DatabaseRepository databaseRepository;

	@BeforeEach
	void setUp(MongoClient mongoClient) {
		Mongo mongo = new Mongo(mongoClient);
		MongoMapper mongoMapper = new MongoMapper();
		databaseRepository = new DatabaseRepository(mongo, mongoMapper);
	}

	@Test
	void it_should_get_all_database() {
		List<Database> dbs = databaseRepository.listDatabases().collect(Collectors.toList());
		assertThat(dbs).isNotEmpty()
				.extracting(Database::getName, Database::isEmpty)
				.containsExactly(
						tuple("marvels", false),
						tuple("movies", false)
				);
	}

	@Test
	void it_should_get_database() {
		Database db = databaseRepository.getDatabase("movies");
		assertThat(db.getName()).isEqualTo("movies");
		assertThat(db.getSizeOnDisk()).isNotZero();
		assertThat(db.isEmpty()).isFalse();
		assertThat(db.getStats().getCollections()).isOne();
		assertThat(db.getStats().getObjects()).isEqualTo(2L);
		assertThat(db.getStats().getDataSize()).isGreaterThan(0);
		assertThat(db.getStats().getStorageSize()).isGreaterThan(0);
		assertThat(db.getStats().getIndexes()).isOne();
		assertThat(db.getStats().getIndexSize()).isGreaterThan(0);
	}

	@Test
	void it_should_get_non_existing_database() {
		Database db = databaseRepository.getDatabase("foo_bar");
		assertThat(db.getName()).isEqualTo("foo_bar");
		assertThat(db.getSizeOnDisk()).isZero();
		assertThat(db.isEmpty()).isTrue();
		assertThat(db.getStats().getCollections()).isZero();
		assertThat(db.getStats().getObjects()).isZero();
		assertThat(db.getStats().getDataSize()).isZero();
		assertThat(db.getStats().getStorageSize()).isZero();
		assertThat(db.getStats().getIndexes()).isZero();
		assertThat(db.getStats().getIndexSize()).isZero();
	}

	@Test
	void it_should_not_get_admin_database() {
		assertThatThrownBy(() -> databaseRepository.getDatabase("admin")).isInstanceOf(IllegalMongoDatabaseAccessException.class);
	}

	@Test
	void it_should_not_get_local_database() {
		assertThatThrownBy(() -> databaseRepository.getDatabase("local")).isInstanceOf(IllegalMongoDatabaseAccessException.class);
	}
}
