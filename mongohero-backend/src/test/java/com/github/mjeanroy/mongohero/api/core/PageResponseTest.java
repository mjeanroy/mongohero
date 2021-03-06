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

package com.github.mjeanroy.mongohero.api.core;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Collections.singleton;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PageResponseTest {

	@Test
	void it_should_create_page_response() {
		List<String> body = asList("one", "two", "three");
		int page = 1;
		int pageSize = 10;
		int total = 100;

		PageResponse<String> response = PageResponse.of(body, page, pageSize, total);

		assertThat(response).isNotNull();
		assertThat(response.getBody()).isEqualTo(body);
		assertThat(response.getPage()).isEqualTo(page);
		assertThat(response.getPageSize()).isEqualTo(pageSize);
		assertThat(response.getTotal()).isEqualTo(total);
	}

	@Test
	void it_should_create_page_response_with_a_total_equal_to_zero() {
		List<String> body = emptyList();
		int page = 1;
		int pageSize = 10;
		int total = 0;

		PageResponse<String> response = PageResponse.of(body, page, pageSize, total);

		assertThat(response).isNotNull();
		assertThat(response.getBody()).isEqualTo(body);
		assertThat(response.getPage()).isEqualTo(page);
		assertThat(response.getPageSize()).isEqualTo(pageSize);
		assertThat(response.getTotal()).isZero();
	}

	@Test
	void it_should_fail_to_create_page_response_with_a_page_less_than_one() {
		assertThatThrownBy(() -> PageResponse.of(singleton("one"), 0, 10, 100)).isInstanceOf(IllegalArgumentException.class).hasMessage("Page must start with 1");
		assertThatThrownBy(() -> PageResponse.of(singleton("one"), -1, 10, 100)).isInstanceOf(IllegalArgumentException.class).hasMessage("Page must start with 1");
	}

	@Test
	void it_should_fail_to_create_page_response_with_a_page_size_less_than_one() {
		assertThatThrownBy(() -> PageResponse.of(singleton("one"), 1, 0, 100)).isInstanceOf(IllegalArgumentException.class).hasMessage("Page size must be at least 1");
		assertThatThrownBy(() -> PageResponse.of(singleton("one"), 1, -1, 100)).isInstanceOf(IllegalArgumentException.class).hasMessage("Page size must be at least 1");
	}

	@Test
	void it_should_fail_to_create_page_response_with_a_total_less_than_zero() {
		assertThatThrownBy(() -> PageResponse.of(singleton("one"), 1, 10, -1)).isInstanceOf(IllegalArgumentException.class).hasMessage("Total must be positive");
	}

	@Test
	void it_should_implement_equals_hash_code() {
		EqualsVerifier.forClass(PageResponse.class).verify();
	}

	@Test
	void it_should_implement_to_string() {
		List<String> body = asList("one", "two", "three");
		int page = 1;
		int pageSize = 10;
		int total = 100;

		PageResponse<String> response = PageResponse.of(body, page, pageSize, total);

		assertThat(response).hasToString(
				"com.github.mjeanroy.mongohero.api.core.PageResponse@" + Integer.toHexString(System.identityHashCode(response)) + "[" +
						"body=[one, two, three]," +
						"page=1," +
						"pageSize=10," +
						"total=100" +
				"]"
		);
	}
}
