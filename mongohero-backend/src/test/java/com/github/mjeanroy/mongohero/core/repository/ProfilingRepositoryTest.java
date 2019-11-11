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

import com.github.mjeanroy.mongohero.core.model.ProfilingStatus;
import com.github.mjeanroy.mongohero.core.mongo.Mongo;
import com.github.mjeanroy.mongohero.core.mongo.MongoMapper;
import com.github.mjeanroy.mongohero.tests.MongoDb32Test;
import com.mongodb.client.MongoClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@MongoDb32Test
class ProfilingRepositoryTest {

	private ProfilingRepository profilingRepository;

	@BeforeEach
	void setUp(MongoClient mongoClient) {
		Mongo mongo = new Mongo(mongoClient);
		MongoMapper mongoMapper = new MongoMapper();
		profilingRepository = new ProfilingRepository(mongo, mongoMapper);
	}

	@Test
	void it_should_get_profiling_status() {
		ProfilingStatus profilingStatus = profilingRepository.getStatus("marvels");
		assertThat(profilingStatus).isNotNull();
		assertThat(profilingStatus.getWas()).isEqualTo(0);
		assertThat(profilingStatus.getSlowms()).isEqualTo(100);
	}

	@Test
	void it_should_set_profiling_level() {
		profilingRepository.setStatus("marvels", 1, 20);

		ProfilingStatus profilingStatus = profilingRepository.getStatus("marvels");
		assertThat(profilingStatus).isNotNull();
		assertThat(profilingStatus.getWas()).isEqualTo(1);
		assertThat(profilingStatus.getSlowms()).isEqualTo(20);
	}
}