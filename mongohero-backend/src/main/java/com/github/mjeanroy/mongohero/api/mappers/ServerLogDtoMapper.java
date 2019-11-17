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
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class ServerLogDtoMapper extends AbstractDtoMapper<ServerLogDto, ServerLog> {

	@Override
	ServerLogDto doMap(ServerLog input) {
		ServerLogDto dto = new ServerLogDto();

		// Put more recent first.
		List<String> rawLogs = new ArrayList<>(input.getLog());
		Collections.reverse(rawLogs);

		dto.setLogs(rawLogs);
		return dto;
	}

	/**
	 * Map given dictionary of log entries (each keys being the host on which these logs have been
	 * read) to a list of outputs.
	 *
	 * @param inputs Input dictionary.
	 * @return List of outputs.
	 */
	public Iterable<ServerLogDto> map(Map<String, ServerLog> inputs) {
		return inputs.entrySet().stream()
				.map(this::doMap)
				.sorted(Comparator.comparing(ServerLogDto::getHost))
				.collect(Collectors.toList());
	}

	private ServerLogDto doMap(Map.Entry<String, ServerLog> input) {
		ServerLogDto output = doMap(input.getValue());
		output.setHost(input.getKey());
		return output;
	}
}
