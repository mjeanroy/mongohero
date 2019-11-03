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

package com.github.mjeanroy.mongohero.core.services;

import com.github.mjeanroy.mongohero.core.model.Operation;
import com.github.mjeanroy.mongohero.core.model.Server;
import com.github.mjeanroy.mongohero.core.model.ServerLog;
import com.github.mjeanroy.mongohero.core.model.ServerParameter;
import com.github.mjeanroy.mongohero.core.repository.ServerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ServerService {

    private final ServerRepository serverRepository;

    @Autowired
    ServerService(ServerRepository serverRepository) {
        this.serverRepository = serverRepository;
    }

    /**
     * Get server information.
     *
     * @return Server information.
     */
    public Server get() {
        return serverRepository.find();
    }

    /**
     * Get available server log.
     *
     * @return Server log.
     */
    public ServerLog getLog() {
        return serverRepository.getLog();
    }

    /**
     * Get configuration parameters.
     *
     * @return Configuration parameters.
     */
    public List<ServerParameter> getParameters() {
        return serverRepository.getParameters().entrySet().stream()
                .map(entry -> new ServerParameter(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }

    /**
     * Get in progress operations.
     *
     * @return In progress operations.
     */
    public Stream<Operation> getCurrentOperations() {
        return serverRepository.getCurrentOp();
    }
}
