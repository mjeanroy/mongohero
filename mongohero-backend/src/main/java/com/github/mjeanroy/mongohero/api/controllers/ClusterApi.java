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

package com.github.mjeanroy.mongohero.api.controllers;

import com.github.mjeanroy.mongohero.api.dto.ClusterDescriptionDto;
import com.github.mjeanroy.mongohero.api.dto.ClusterServerDescriptionDto;
import com.github.mjeanroy.mongohero.api.dto.ServerLogDto;
import com.github.mjeanroy.mongohero.api.dto.ServerParameterDto;
import com.github.mjeanroy.mongohero.api.mappers.ClusterDescriptionDtoMapper;
import com.github.mjeanroy.mongohero.api.mappers.ClusterServerDescriptionDtoMapper;
import com.github.mjeanroy.mongohero.api.mappers.ServerLogDtoMapper;
import com.github.mjeanroy.mongohero.api.mappers.ServerParameterDtoMapper;
import com.github.mjeanroy.mongohero.core.model.ServerLog;
import com.github.mjeanroy.mongohero.core.model.ServerParameter;
import com.github.mjeanroy.mongohero.core.services.ClusterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class ClusterApi {

	private final ClusterService clusterService;
	private final ClusterDescriptionDtoMapper clusterDescriptionDtoMapper;
	private final ClusterServerDescriptionDtoMapper clusterServerDescriptionDtoMapper;
	private final ServerLogDtoMapper serverLogDtoMapper;
	private final ServerParameterDtoMapper serverParameterDtoMapper;

	@Autowired
	ClusterApi(
			ClusterService clusterService,
			ClusterDescriptionDtoMapper clusterDescriptionDtoMapper,
			ClusterServerDescriptionDtoMapper clusterServerDescriptionDtoMapper,
			ServerLogDtoMapper serverLogDtoMapper,
			ServerParameterDtoMapper serverParameterDtoMapper) {

		this.clusterService = clusterService;
		this.clusterDescriptionDtoMapper = clusterDescriptionDtoMapper;
		this.clusterServerDescriptionDtoMapper = clusterServerDescriptionDtoMapper;
		this.serverLogDtoMapper = serverLogDtoMapper;
		this.serverParameterDtoMapper = serverParameterDtoMapper;
	}

	@GetMapping("/api/cluster")
	public ClusterDescriptionDto get() {
		return clusterDescriptionDtoMapper.map(
				clusterService.getClusterDescription()
		);
	}

	@GetMapping("/api/cluster/servers")
	public Iterable<ClusterServerDescriptionDto> servers() {
		return clusterServerDescriptionDtoMapper.mapToList(
				clusterService.getServers()
		);
	}

	@GetMapping("/api/cluster/logs")
	public Map<String, ServerLogDto> getLogs() {
		Map<String, ServerLog> outputs = clusterService.getLog();
		return outputs.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey,
				e -> serverLogDtoMapper.map(e.getValue())
		));
	}

	@GetMapping("/api/cluster/parameters")
	public Map<String, List<ServerParameterDto>> getParameters() {
		Map<String, Iterable<ServerParameter>> outputs = clusterService.getParameters();
		return outputs.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey,
				e -> serverParameterDtoMapper.mapToList(e.getValue())
		));
	}
}
