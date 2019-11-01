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

import com.github.mjeanroy.mongohero.api.core.PageResponse;
import com.github.mjeanroy.mongohero.api.core.PageParam;
import com.github.mjeanroy.mongohero.api.core.SortParam;
import com.github.mjeanroy.mongohero.api.dto.ProfileQueryDto;
import com.github.mjeanroy.mongohero.api.dto.ProfilingStatusDto;
import com.github.mjeanroy.mongohero.api.mappers.ProfileQueryDtoMapper;
import com.github.mjeanroy.mongohero.api.mappers.ProfilingStatusDtoMapper;
import com.github.mjeanroy.mongohero.core.model.ProfileQuery;
import com.github.mjeanroy.mongohero.core.query.Page;
import com.github.mjeanroy.mongohero.core.query.PageResult;
import com.github.mjeanroy.mongohero.core.query.Sort;
import com.github.mjeanroy.mongohero.core.services.ProfilingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.github.mjeanroy.mongohero.core.query.Sort.Order.DESC;

@RestController
public class ProfilingApi {

    private final ProfilingService profilingService;
    private final ProfileQueryDtoMapper profileQueryDtoMapper;
    private final ProfilingStatusDtoMapper profilingStatusDtoMapper;

    @Autowired
    ProfilingApi(
            ProfilingService profilingService,
            ProfileQueryDtoMapper profileQueryDtoMapper,
            ProfilingStatusDtoMapper profilingStatusDtoMapper) {

        this.profilingService = profilingService;
        this.profileQueryDtoMapper = profileQueryDtoMapper;
        this.profilingStatusDtoMapper = profilingStatusDtoMapper;
    }

    @GetMapping("/api/databases/{db}/profiling/queries")
    public PageResponse<ProfileQueryDto> getQueries(
            @PathVariable("db") String db,
            @PageParam Page page,
            @SortParam(defaultName = "millis", defaultOrder = DESC) Sort sort) {

        PageResult<ProfileQuery> results = profilingService.findSlowQueries(db, page, sort);
        List<ProfileQueryDto> dtos = profileQueryDtoMapper.mapToList(results.getResults());
        return PageResponse.of(dtos, results.page(), results.pageSize(), results.getTotal());
    }

    @GetMapping("/api/profiling/status")
    public ProfilingStatusDto getStatus() {
        return profilingStatusDtoMapper.map(
                profilingService.getProfilingStatus()
        );
    }

    @PutMapping("/api/profiling/status")
    public ProfilingStatusDto updateProfilingStatus(@RequestBody @Valid ProfilingStatusDto profilingStatus) {
        return profilingStatusDtoMapper.map(
                profilingService.updateProfilingStatus(
                        profilingStatus.getLevel(),
                        profilingStatus.getSlowMs()
                )
        );
    }
}
