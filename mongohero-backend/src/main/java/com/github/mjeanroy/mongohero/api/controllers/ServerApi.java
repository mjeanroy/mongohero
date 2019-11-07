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

import com.github.mjeanroy.mongohero.api.dto.OperationDto;
import com.github.mjeanroy.mongohero.api.dto.ReplicationStatusDto;
import com.github.mjeanroy.mongohero.api.dto.ServerDto;
import com.github.mjeanroy.mongohero.api.dto.ServerParameterDto;
import com.github.mjeanroy.mongohero.api.mappers.OperationDtoMapper;
import com.github.mjeanroy.mongohero.api.mappers.ReplicationStatusDtoMapper;
import com.github.mjeanroy.mongohero.api.mappers.ServerDtoMapper;
import com.github.mjeanroy.mongohero.api.mappers.ServerParameterDtoMapper;
import com.github.mjeanroy.mongohero.core.model.Server;
import com.github.mjeanroy.mongohero.core.model.ServerLog;
import com.github.mjeanroy.mongohero.core.services.ServerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
public class ServerApi {

    private final ServerService serverService;
    private final ServerDtoMapper serverDtoMapper;
    private final ServerParameterDtoMapper serverParameterDtoMapper;
    private final OperationDtoMapper operationDtoMapper;
    private final ReplicationStatusDtoMapper replicationStatusDtoMapper;

    @Autowired
    ServerApi(
            ServerService serverService,
            ServerDtoMapper serverDtoMapper,
            ServerParameterDtoMapper serverParameterDtoMapper,
            OperationDtoMapper operationDtoMapper,
            ReplicationStatusDtoMapper replicationStatusDtoMapper) {

        this.serverService = serverService;
        this.serverDtoMapper = serverDtoMapper;
        this.serverParameterDtoMapper = serverParameterDtoMapper;
        this.operationDtoMapper = operationDtoMapper;
        this.replicationStatusDtoMapper = replicationStatusDtoMapper;
    }

    @GetMapping("/api/server")
    public ServerDto get() {
        Server server = serverService.get();
        return serverDtoMapper.map(server);
    }

    @GetMapping("/api/server/log")
    public List<String> getLogs() {
        ServerLog log = serverService.getLog();
        List<String> rawLogs = log.getLog();

        // Display most recent first.
        Collections.reverse(rawLogs);

        return rawLogs;
    }

    @GetMapping("/api/server/parameters")
    public List<ServerParameterDto> getParameters() {
        return serverParameterDtoMapper.mapToList(
                serverService.getParameters()
        );
    }

    @GetMapping("/api/server/operations")
    public List<OperationDto> getOperations() {
        return operationDtoMapper.mapToList(
                serverService.getCurrentOperations()
        );
    }

    @GetMapping("/api/server/replication")
    public ReplicationStatusDto getReplicationStatus() {
        return replicationStatusDtoMapper.map(
                serverService.getReplicationStatusOrFail()
        );
    }
}
