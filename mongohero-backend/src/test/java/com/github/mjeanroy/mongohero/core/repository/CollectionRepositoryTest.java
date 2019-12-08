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

import com.github.mjeanroy.mongohero.core.model.Collection;
import com.github.mjeanroy.mongohero.core.model.IndexStat;
import com.github.mjeanroy.mongohero.core.mongo.Mongo;
import com.github.mjeanroy.mongohero.core.mongo.MongoMapper;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

class CollectionRepositoryTest extends AbstractRepositoryTest {

	private CollectionRepository collectionRepository;

	@Override
	void initialize(Mongo mongo, MongoMapper mongoMapper) {
		collectionRepository = new CollectionRepository(mongo, mongoMapper);
	}

	@Test
	void it_should_list_database_collection() {
		List<Collection> collections = collectionRepository.listCollections("marvels").collect(Collectors.toList());
		assertThat(collections).hasSize(2)
				.extracting(
						Collection::getDatabase,
						Collection::getName,
						Collection::getNs
				)
				.contains(
						tuple("marvels", "avengers", "marvels.avengers"),
						tuple("marvels", "movies", "marvels.movies")
				);
	}

	@Test
	void it_should_get_collection_index_stats() {
		List<IndexStat> indexes = collectionRepository.indexStats("marvels", "movies").collect(Collectors.toList());

		assertThat(indexes).hasSize(1)
				.extracting(IndexStat::getName)
				.containsOnly("_id_");
	}
}
