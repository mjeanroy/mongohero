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

import com.github.mjeanroy.mongohero.core.model.Database;
import com.github.mjeanroy.mongohero.mongo.MongoMapper;
import com.github.mjeanroy.mongohero.tests.MongoDb32Test;
import com.mongodb.client.MongoClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

@MongoDb32Test
class DatabaseRepositoryTest {

	private DatabaseRepository databaseRepository;

	@BeforeEach
	void setUp(MongoClient mongoClient) {
		MongoMapper mongoMapper = new MongoMapper();
		databaseRepository = new DatabaseRepository(mongoClient, mongoMapper);
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
		Optional<Database> maybeDb = databaseRepository.getDatabase("movies");
		assertThat(maybeDb).isPresent();

		Database db = maybeDb.get();
		assertThat(db.getName()).isEqualTo("movies");
		assertThat(db.getSizeOnDisk()).isNotZero();
	}

	@Test
	void it_should_not_get_admin_database() {
		Optional<Database> maybeDb = databaseRepository.getDatabase("admin");
		assertThat(maybeDb).isNotPresent();
	}

	@Test
	void it_should_not_get_local_database() {
		Optional<Database> maybeDb = databaseRepository.getDatabase("local");
		assertThat(maybeDb).isNotPresent();
	}
}
