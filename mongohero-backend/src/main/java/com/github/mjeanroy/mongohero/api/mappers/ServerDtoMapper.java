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

import com.github.mjeanroy.mongohero.api.dto.ServerDto;
import com.github.mjeanroy.mongohero.core.model.Server;
import com.github.mjeanroy.mongohero.core.services.DatabaseService;
import com.github.mjeanroy.mongohero.core.services.ProfilingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ServerDtoMapper extends AbstractDtoMapper<ServerDto, Server> {

    private final ServerConnectionsDtoMapper serverConnectionsDtoMapper;
    private final ServerStorageEngineDtoMapper serverStorageEngineDtoMapper;
    private final DatabaseService databaseService;
    private final ProfilingService profilingService;
    private final DatabaseDtoMapper databaseDtoMapper;
    private final ProfilingStatusDtoMapper profilingStatusDtoMapper;

    @Autowired
    ServerDtoMapper(
            ServerConnectionsDtoMapper serverConnectionsDtoMapper,
            ServerStorageEngineDtoMapper serverStorageEngineDtoMapper,
            DatabaseService databaseService,
            ProfilingService profilingService,
            DatabaseDtoMapper databaseDtoMapper,
            ProfilingStatusDtoMapper profilingStatusDtoMapper) {

        this.serverConnectionsDtoMapper = serverConnectionsDtoMapper;
        this.serverStorageEngineDtoMapper = serverStorageEngineDtoMapper;
        this.profilingService = profilingService;
        this.databaseService = databaseService;
        this.databaseDtoMapper = databaseDtoMapper;
        this.profilingStatusDtoMapper = profilingStatusDtoMapper;
    }

    @Override
    ServerDto doMap(Server server) {
        ServerDto dto = new ServerDto();
        dto.setHost(server.getHost());
        dto.setUptime(server.getUptime());
        dto.setVersion(server.getVersion());
        dto.setConnections(serverConnectionsDtoMapper.map(server.getConnections()));
        dto.setStorageEngine(serverStorageEngineDtoMapper.map(server.getStorageEngine()));

        dto.setDatabases(
                databaseDtoMapper.mapToList(databaseService.findAll())
        );

        dto.setProfilingStatus(
                profilingStatusDtoMapper.map(profilingService.getProfilingStatus())
        );

        return dto;
    }
}
