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

class SortTest {

	@Test
	void it_should_create_sort() {
		String name = "id";
		Sort.Order order = Sort.Order.ASC;
		Sort sort = Sort.of(name, order);

		assertThat(sort).isNotNull();
		assertThat(sort.getName()).isEqualTo(name);
		assertThat(sort.order()).isEqualTo(1);
		assertThat(sort.isAsc()).isTrue();
		assertThat(sort.isDesc()).isFalse();
	}

	@Test
	void it_should_create_asc_sort() {
		String name = "id";
		Sort sort = Sort.asc(name);

		assertThat(sort).isNotNull();
		assertThat(sort.getName()).isEqualTo(name);
		assertThat(sort.order()).isEqualTo(1);
		assertThat(sort.isAsc()).isTrue();
		assertThat(sort.isDesc()).isFalse();
	}

	@Test
	void it_should_create_desc_sort() {
		String name = "id";
		Sort sort = Sort.desc(name);

		assertThat(sort).isNotNull();
		assertThat(sort.getName()).isEqualTo(name);
		assertThat(sort.order()).isEqualTo(-1);
		assertThat(sort.isAsc()).isFalse();
		assertThat(sort.isDesc()).isTrue();
	}

	@Test
	void it_should_fail_to_create_sort_with_invalid_name() {
		assertThatThrownBy(() -> Sort.asc(null)).isInstanceOf(NullPointerException.class).hasMessage("Sort field must be defined");
		assertThatThrownBy(() -> Sort.asc("")).isInstanceOf(IllegalArgumentException.class).hasMessage("Sort field must be defined");
		assertThatThrownBy(() -> Sort.asc("   ")).isInstanceOf(IllegalArgumentException.class).hasMessage("Sort field must be defined");

		assertThatThrownBy(() -> Sort.desc(null)).isInstanceOf(NullPointerException.class).hasMessage("Sort field must be defined");
		assertThatThrownBy(() -> Sort.desc("")).isInstanceOf(IllegalArgumentException.class).hasMessage("Sort field must be defined");
		assertThatThrownBy(() -> Sort.desc("   ")).isInstanceOf(IllegalArgumentException.class).hasMessage("Sort field must be defined");
	}

	@Test
	void it_should_fail_to_create_sort_with_invalid_order() {
		assertThatThrownBy(() -> Sort.of("id", null)).isInstanceOf(NullPointerException.class).hasMessage("Sort order must be defined");
	}

	@Test
	void it_should_implement_equals_hash_code() {
		EqualsVerifier.forClass(Sort.class).verify();
	}

	@Test
	void it_should_implement_to_string() {
		String name = "id";
		Sort sort = Sort.desc(name);

		assertThat(sort).hasToString(
				"com.github.mjeanroy.mongohero.core.query.Sort@" + Integer.toHexString(System.identityHashCode(sort)) + "[" +
						"name=id," +
						"order=DESC" +
				"]"
		);
	}
}
