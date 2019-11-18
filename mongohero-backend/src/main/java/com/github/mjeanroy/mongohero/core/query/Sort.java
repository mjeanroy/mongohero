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

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Objects;

import static com.github.mjeanroy.mongohero.commons.PreConditions.notBlank;
import static com.github.mjeanroy.mongohero.commons.PreConditions.notNull;

/**
 * Sort Descriptor.
 */
public final class Sort {

	/**
	 * Create sort with given direction.
	 *
	 * @param name Sort field.
	 * @param order Sort order.
	 * @return The sort.
	 */
	public static Sort of(String name, Order order) {
		return new Sort(name, order);
	}

	/**
	 * Create sort with ascending direction.
	 *
	 * @param name Sort field.
	 * @return The sort.
	 */
	public static Sort asc(String name) {
		return new Sort(name, Order.ASC);
	}

	/**
	 * Create sort with descending direction.
	 *
	 * @param name Sort field.
	 * @return The sort.
	 */
	public static Sort desc(String name) {
		return new Sort(name, Order.DESC);
	}

	/**
	 * The order direction.
	 */
	public enum Order {
		/**
		 * Ascending Order.
		 */
		ASC(1),

		/**
		 * Descending Order.
		 */
		DESC(-1);

		/**
		 * The sort factor:
		 *
		 * <ul>
		 *   <li>{@code 1} for an ascending order.</li>
		 *   <li>{@code -1} for a descending order.</li>
		 * </ul>
		 */
		private final int value;

		Order(int value) {
			this.value = value;
		}
	}

	/**
	 * Sort field.
	 */
	private final String name;

	/**
	 * Sort order.
	 */
	private final Order order;

	/**
	 * Create sort.
	 *
	 * @param name  Sort name.
	 * @param order Sort order.
	 */
	private Sort(String name, Order order) {
		this.name = notBlank(name, "Sort field must be defined");
		this.order = notNull(order, "Sort order must be defined");
	}

	/**
	 * Get {@link #name}
	 *
	 * @return {@link #name}
	 */
	public String getName() {
		return name;
	}

	/**
	 * Check of this sort specifies an ascending order.
	 *
	 * @return {@code true} for an ascending order, {@code false} otherwise.
	 */
	public boolean isAsc() {
		return order.value > 0;
	}

	/**
	 * Check of this sort specifies an descending order.
	 *
	 * @return {@code true} for an descending order, {@code false} otherwise.
	 */
	public boolean isDesc() {
		return order.value < 0;
	}

	/**
	 * The order value:
	 *
	 * <ul>
	 *   <li>{@code 1} for an ascending order.</li>
	 *   <li>{@code -1} for a descending order.</li>
	 * </ul>
	 *
	 * @return The order factor.
	 */
	public int order() {
		return order.value;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this)
				.append("name", name)
				.append("order", order)
				.build();
	}

	@Override
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}

		if (o instanceof Sort) {
			Sort s = (Sort) o;
			return Objects.equals(name, s.name) && Objects.equals(order, s.order);
		}

		return false;
	}

	@Override
	public int hashCode() {
		return Objects.hash(name, order);
	}
}
