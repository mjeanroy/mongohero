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

import com.github.mjeanroy.mongohero.core.model.Operation;
import com.github.mjeanroy.mongohero.core.model.Server;
import com.github.mjeanroy.mongohero.core.model.ServerLog;
import com.github.mjeanroy.mongohero.core.mongo.Mongo;
import com.github.mjeanroy.mongohero.core.mongo.MongoMapper;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

class ServerRepositoryTest extends AbstractRepositoryTest {

	private ServerRepository serverRepository;

	@Override
	void initialize(Mongo mongo, MongoMapper mongoMapper) {
		serverRepository = new ServerRepository(mongo, mongoMapper);
	}

	@Test
	void it_should_get_server_status() {
		Server server = serverRepository.serverStatus();

		assertThat(server).isNotNull();
		assertThat(server.getHost()).isNotEmpty();
		assertThat(server.getVersion()).isEqualTo("3.2.16");

		assertThat(server.getStorageEngine()).isNotNull();
		assertThat(server.getStorageEngine().getName()).isEqualTo("wiredTiger");
		assertThat(server.getStorageEngine().isPersistent()).isTrue();
		assertThat(server.getStorageEngine().isSupportsCommittedReads()).isTrue();
	}

	@Test
	void it_should_get_logs() {
		ServerLog serverLog = serverRepository.getLog();
		assertThat(serverLog).isNotNull();
		assertThat(serverLog.getTotalLinesWritten()).isGreaterThan(0);
		assertThat(serverLog.getLog()).isNotEmpty();
	}

	@Test
	void it_should_get_parameters() {
		Map<String, Object> parameters = serverRepository.getParameters();
		assertThat(parameters).isNotEmpty();
	}

	@Test
	void it_should_get_server_operations() {
		List<Operation> operations = serverRepository.getCurrentOp().collect(Collectors.toList());
		assertThat(operations).isNotEmpty();
	}
}
