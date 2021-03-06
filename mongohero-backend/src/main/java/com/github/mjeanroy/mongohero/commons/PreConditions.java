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

public final class PreConditions {

	private PreConditions() {
	}

	/**
	 * Ensure that given value is not null.
	 *
	 * @param value   The value.
	 * @param message The error message.
	 * @param <T>     Type of given value.
	 * @return The original value.
	 * @throws NullPointerException If {@code value} is {@code null}.
	 */
	public static <T> T notNull(T value, String message) {
		if (value == null) {
			throw new NullPointerException(message);
		}

		return value;
	}

	/**
	 * Ensure that given value is not {@code null}, empty or blank.
	 *
	 * @param value   The value.
	 * @param message The error message.
	 * @return The original value.
	 * @throws NullPointerException If {@code value} is {@code null}.
	 * @throws IllegalArgumentException If {@code value} is empty or blank.
	 */
	public static String notBlank(String value, String message) {
		notNull(value, message);

		if (!value.isEmpty()) {
			for (char c : value.toCharArray()) {
				if (!Character.isWhitespace(c)) {
					return value;
				}
			}
		}

		throw new IllegalArgumentException(message);
	}

	/**
	 * Ensure that given value is greater than or equal given minimum value.
	 *
	 * @param minValue The minimum value.
	 * @param value    The value.
	 * @param message  The error message.
	 * @return The original value.
	 * @throws IllegalArgumentException If {@code value} is strictly less than {@code minValue}.
	 */
	public static int gte(int minValue, int value, String message) {
		if (value < minValue) {
			throw new IllegalArgumentException(message);
		}

		return value;
	}

	/**
	 * Ensure that given value is greater than or equal given minimum value.
	 *
	 * @param minValue The minimum value.
	 * @param value    The value.
	 * @param message  The error message.
	 * @return The original value.
	 * @throws IllegalArgumentException If {@code value} is strictly less than {@code minValue}.
	 */
	public static long gte(long minValue, long value, String message) {
		if (value < minValue) {
			throw new IllegalArgumentException(message);
		}

		return value;
	}
}
