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

import com.github.mjeanroy.mongohero.core.mongo.Mongo;
import com.github.mjeanroy.mongohero.core.mongo.MongoClientFactory;
import com.github.mjeanroy.mongohero.core.mongo.MongoMapper;
import com.github.mjeanroy.mongohero.tests.MongoDb32Test;
import com.github.mjeanroy.mongohero.tests.MongoDbContainerDescriptor;
import org.junit.jupiter.api.BeforeEach;

import static com.github.mjeanroy.mongohero.core.tests.MongoTestUtils.createMongoClientFactory;

@MongoDb32Test
abstract class AbstractRepositoryTest {

	@BeforeEach
	void setUp(MongoDbContainerDescriptor mongoDbContainerDescriptor) {
		MongoMapper mongoMapper = new MongoMapper();
		MongoClientFactory mongoClientFactory = createMongoClientFactory(mongoDbContainerDescriptor);
		Mongo mongo = new Mongo(mongoClientFactory);

		initialize(mongo, mongoMapper);
	}

	abstract void initialize(Mongo mongo, MongoMapper mongoMapper);
}
