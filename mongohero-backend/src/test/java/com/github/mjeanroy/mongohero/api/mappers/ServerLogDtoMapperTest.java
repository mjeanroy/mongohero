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

package com.github.mjeanroy.mongohero.api.mappers;

import com.github.mjeanroy.mongohero.api.dto.ServerLogDto;
import com.github.mjeanroy.mongohero.core.model.ServerLog;
import com.github.mjeanroy.mongohero.core.tests.builders.ServerLogBuilder;
import org.junit.jupiter.api.BeforeEach;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class ServerLogDtoMapperTest extends AbstractDtoMapperTest<ServerLogDto, ServerLog> {

	private ServerLogDtoMapper mapper;

	@BeforeEach
	void setUp() {
		mapper = new ServerLogDtoMapper();
	}

	@Override
	ServerLog givenInput() {
		return new ServerLogBuilder()
				.addLog(UUID.randomUUID().toString())
				.addLog(UUID.randomUUID().toString())
				.addLog(UUID.randomUUID().toString())
				.addLog(UUID.randomUUID().toString())
				.build();
	}

	@Override
	void verifyMapping(ServerLog input, ServerLogDto output) {
		List<String> inputLogs = input.getLog();
		List<String> outputLogs = output.getLogs();
		assertThat(outputLogs).hasSameSizeAs(inputLogs);

		int size = outputLogs.size();
		for (int i = 0; i < size; ++i) {
			assertThat(outputLogs.get(i)).isEqualTo(inputLogs.get(size - 1 - i));
		}
	}

	@Override
	ServerLogDtoMapper getMapper() {
		return mapper;
	}
}
