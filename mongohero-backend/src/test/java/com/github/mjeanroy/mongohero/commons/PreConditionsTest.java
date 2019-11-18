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

package com.github.mjeanroy.mongohero.commons;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PreConditionsTest {

	@Nested
	class NotNull {
		@Test
		void it_should_fail_with_null() {
			String value = null;
			String message = "message";
			assertThatThrownBy(() -> PreConditions.notNull(value, message)).isInstanceOf(NullPointerException.class).hasMessage(message);
		}

		@Test
		void it_should_returns_original_value_without_null() {
			String value = "test";
			String message = "message";
			assertThat(PreConditions.notNull(value, message)).isSameAs(value);
		}
	}

	@Nested
	class GteInt {
		@Test
		void it_should_fail_with_less_value() {
			int minValue = 1;
			int value = 0;
			String message = "message";
			assertThatThrownBy(() -> PreConditions.gte(minValue, value, message)).isInstanceOf(IllegalArgumentException.class).hasMessage(message);
		}

		@Test
		void it_should_returns_original_value_with_valid_value() {
			String message = "message";
			assertThat(PreConditions.gte(0, 0, message)).isEqualTo(0);
			assertThat(PreConditions.gte(0, 1, message)).isEqualTo(1);
		}
	}

	@Nested
	class GteLong {
		@Test
		void it_should_fail_with_less_value() {
			long minValue = 1;
			long value = 0;
			String message = "message";
			assertThatThrownBy(() -> PreConditions.gte(minValue, value, message)).isInstanceOf(IllegalArgumentException.class).hasMessage(message);
		}

		@Test
		void it_should_returns_original_value_with_valid_value() {
			String message = "message";
			assertThat(PreConditions.gte(0L, 0L, message)).isEqualTo(0L);
			assertThat(PreConditions.gte(0L, 1L, message)).isEqualTo(1L);
		}
	}
}
