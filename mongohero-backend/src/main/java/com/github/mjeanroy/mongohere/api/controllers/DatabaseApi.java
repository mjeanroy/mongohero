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

package com.github.mjeanroy.mongohere.api.controllers;

import com.github.mjeanroy.mongohere.api.dto.DatabaseDto;
import com.github.mjeanroy.mongohere.api.mappers.DatabaseDtoMapper;
import com.github.mjeanroy.mongohere.core.services.DatabaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;

@RestController
public class DatabaseApi {

    private final DatabaseService databaseService;
    private final DatabaseDtoMapper databaseDtoMapper;

    @Autowired
    DatabaseApi(
            DatabaseService databaseService,
            DatabaseDtoMapper databaseDtoMapper) {

        this.databaseService = databaseService;
        this.databaseDtoMapper = databaseDtoMapper;
    }

    @GetMapping("/api/databases")
    public Iterable<DatabaseDto> getAll() {
        return databaseService.findAll().map(databaseDtoMapper::map).collect(Collectors.toList());
    }

    @GetMapping("/api/databases/{name}")
    public DatabaseDto getOne(@PathVariable("name") String name) {
        return databaseDtoMapper.map(databaseService.findOneOrFail(name));
    }
}
