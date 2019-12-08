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

package com.github.mjeanroy.mongohero.core.tests;

import com.github.mjeanroy.mongohero.core.mongo.MongoClientFactory;
import com.github.mjeanroy.mongohero.core.mongo.MongoDbOptions;
import com.github.mjeanroy.mongohero.core.mongo.MongoDbProperties;
import com.github.mjeanroy.mongohero.tests.junit.MongoDbContainerDescriptor;

public final class MongoTestUtils {

	// Ensure non instantiation.
	private MongoTestUtils() {
	}

	/**
	 * Create the default {@link MongoClientFactory} for unit testing.
	 *
	 * @param descriptor MongoDB container descriptor.
	 * @return The {@link MongoClientFactory} instance.
	 */
	public static MongoClientFactory createMongoClientFactory(MongoDbContainerDescriptor descriptor) {
		MongoDbProperties mongoDbProperties = toMongoDbProperties(descriptor);
		return new MongoClientFactory(mongoDbProperties);
	}

	/**
	 * Create MongoDB properties from given {@link MongoDbContainerDescriptor}.
	 *
	 * @param descriptor The MongoDB container descriptor.
	 * @return The MongoDB properties.
	 */
	private static MongoDbProperties toMongoDbProperties(MongoDbContainerDescriptor descriptor) {
		String host = descriptor.getHost() + ":" + descriptor.getPort();
		String replicaSet = descriptor.getReplicaSet();
		String user = null;
		String password = null;
		String admin = "admin";
		MongoDbOptions options = createDefaultOptions();
		boolean ssl = descriptor.isSsl();
		return new MongoDbProperties(
				host,
				replicaSet,
				user,
				password,
				admin,
				ssl,
				options
		);
	}

	/**
	 * Create default MongoDB options for unit testing.
	 *
	 * @return Default options.
	 */
	private static MongoDbOptions createDefaultOptions() {
		String connectionMode = null;
		String readPreference = null;
		String readConcernLevel = null;
		int maxPoolSize = 10;
		int minPoolSize = 0;
		int connectTimeoutMs = -1;
		int readTimeoutMs = -1;
		int maxWaitQueueSize = -1;
		return new MongoDbOptions(
				connectionMode,
				readPreference,
				readConcernLevel,
				maxPoolSize,
				minPoolSize,
				connectTimeoutMs,
				readTimeoutMs,
				maxWaitQueueSize
		);
	}
}
