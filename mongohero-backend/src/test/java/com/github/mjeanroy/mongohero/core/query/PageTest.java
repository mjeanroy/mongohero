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

package com.github.mjeanroy.mongohero.core.query;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PageTest {

	@Test
	void it_should_create_page() {
		int pageNumber = 1;
		int pageSize = 10;
		Page page = Page.of(pageNumber, pageSize);

		assertThat(page).isNotNull();
		assertThat(page.getPage()).isEqualTo(pageNumber);
		assertThat(page.getPageSize()).isEqualTo(pageSize);
		assertThat(page.getOffset()).isZero();
	}

	@Test
	void it_should_compute_offset() {
		int pageNumber = 3;
		int pageSize = 10;
		Page page = Page.of(pageNumber, pageSize);

		assertThat(page).isNotNull();
		assertThat(page.getPage()).isEqualTo(pageNumber);
		assertThat(page.getPageSize()).isEqualTo(pageSize);
		assertThat(page.getOffset()).isEqualTo(20);
	}

	@Test
	void it_should_fail_to_create_page_with_a_page_number_less_than_one() {
		assertThatThrownBy(() -> Page.of(0, 10)).isInstanceOf(IllegalArgumentException.class).hasMessage("Page must starts with 1");
		assertThatThrownBy(() -> Page.of(-1, 10)).isInstanceOf(IllegalArgumentException.class).hasMessage("Page must starts with 1");
	}

	@Test
	void it_should_fail_to_create_page_with_a_page_size_less_than_one() {
		assertThatThrownBy(() -> Page.of(1, 0)).isInstanceOf(IllegalArgumentException.class).hasMessage("Page size must be at least 1");
		assertThatThrownBy(() -> Page.of(1, -1)).isInstanceOf(IllegalArgumentException.class).hasMessage("Page size must be at least 1");
	}

	@Test
	void it_should_implement_equals_hash_code() {
		EqualsVerifier.forClass(Page.class).verify();
	}

	@Test
	void it_should_implement_to_string() {
		int pageNumber = 1;
		int pageSize = 10;
		Page page = Page.of(pageNumber, pageSize);

		assertThat(page).hasToString(
				"com.github.mjeanroy.mongohero.core.query.Page@" + Integer.toHexString(System.identityHashCode(page)) + "[" +
						"page=1," +
						"pageSize=10" +
				"]"
		);
	}
}
