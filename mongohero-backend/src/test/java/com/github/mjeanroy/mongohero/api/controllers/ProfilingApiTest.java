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
import com.github.mjeanroy.mongohero.api.dto.ProfileQueryDto;
import com.github.mjeanroy.mongohero.api.mappers.ProfileQueryDtoMapper;
import com.github.mjeanroy.mongohero.api.mappers.ProfilingStatusDtoMapper;
import com.github.mjeanroy.mongohero.core.model.ProfileQuery;
import com.github.mjeanroy.mongohero.core.query.Page;
import com.github.mjeanroy.mongohero.core.query.PageResult;
import com.github.mjeanroy.mongohero.core.query.ProfileQueryFilter;
import com.github.mjeanroy.mongohero.core.query.Sort;
import com.github.mjeanroy.mongohero.core.services.ProfilingService;
import com.github.mjeanroy.mongohero.core.tests.builders.ProfileQueryBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.List;
import java.util.Map;

import static com.github.mjeanroy.mongohero.tests.commons.CollectionTestUtils.toList;
import static java.util.Arrays.asList;
import static java.util.Collections.singletonMap;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ProfilingApiTest {

	private ProfilingService profilingService;
	private ProfilingApi profilingApi;

	@BeforeEach
	void setUp() {
		ProfileQueryDtoMapper profileQueryDtoMapper = new ProfileQueryDtoMapper();
		ProfilingStatusDtoMapper profilingStatusDtoMapper = new ProfilingStatusDtoMapper();

		profilingService = mock(ProfilingService.class);
		profilingApi = new ProfilingApi(
				profilingService,
				profileQueryDtoMapper,
				profilingStatusDtoMapper
		);
	}

	@Test
	void it_should_get_profiling_queries() {
		String db = "marvels";
		String op = null;
		Page page = Page.of(1, 50);
		Sort sort = Sort.desc("millis");

		givenProfileQueries(db, page, sort);

		Map<String, PageResponse<ProfileQueryDto>> response = profilingApi.getQueries(db, op, page, sort);
		assertResponse(response);

		PageResponse<ProfileQueryDto> pageResponse = response.values().iterator().next();
		assertPageResponse(pageResponse);

		List<ProfileQueryDto> responseBody = toList(pageResponse.getBody());
		assertPageResponseBody(responseBody);
		assertProfileQueryFilter(db, page, sort);
	}

	private void assertProfileQueryFilter(String db, Page page, Sort sort) {
		ArgumentCaptor<ProfileQueryFilter> argFilter = ArgumentCaptor.forClass(ProfileQueryFilter.class);
		verify(profilingService).findSlowQueries(eq(db), argFilter.capture(), eq(page), eq(sort));

		ProfileQueryFilter filter = argFilter.getValue();
		assertThat(filter).isNotNull();
		assertThat(filter.getOp()).isNull();
	}

	private void givenProfileQueries(String db, Page page, Sort sort) {
		List<ProfileQuery> queries = asList(
				givenProfileQuery("marvels.movies", 100),
				givenProfileQuery("marvels.avengers", 50),
				givenProfileQuery("marvels.movies", 30)
		);

		when(profilingService.findSlowQueries(eq(db), any(ProfileQueryFilter.class), eq(page), eq(sort))).thenAnswer(invocation ->
				singletonMap("127.0.0.1:27017", PageResult.of(queries.stream(), page, sort, 3))
		);
	}

	private static ProfileQuery givenProfileQuery(String ns, int millis) {
		return new ProfileQueryBuilder()
				.withNs(ns)
				.withMillis(millis)
				.build();
	}

	private static void assertResponse(Map<String, PageResponse<ProfileQueryDto>> response) {
		assertThat(response).isNotNull().hasSize(1).containsKey("127.0.0.1:27017");
	}

	private static void assertPageResponse(PageResponse<ProfileQueryDto> pageResponse) {
		assertThat(pageResponse).isNotNull();
		assertThat(pageResponse.getPageSize()).isEqualTo(50);
		assertThat(pageResponse.getPage()).isEqualTo(1);
		assertThat(pageResponse.getTotal()).isEqualTo(3);
	}

	private static void assertPageResponseBody(List<ProfileQueryDto> dtos) {
		assertThat(dtos).hasSize(3)
				.extracting(
						ProfileQueryDto::getNs,
						ProfileQueryDto::getMillis
				)
				.contains(
						tuple("marvels.movies", 100),
						tuple("marvels.avengers", 50),
						tuple("marvels.movies", 30)
				);
	}
}
