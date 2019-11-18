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

import static com.github.mjeanroy.mongohero.commons.PreConditions.gte;

/**
 * A page that can be used to define a page number and a page size (he offset is automatically
 * computed using {@link #page} and {@link #pageSize}).
 */
public final class Page {

	/**
	 * Create page.
	 *
	 * @param page     Page number (must be {@code >= 1}).
	 * @param pageSize Page size (must be {@code >= 1}).
	 * @return The page.
	 */
	public static Page of(int page, int pageSize) {
		return new Page(page, pageSize);
	}

	/**
	 * The page to query.
	 */
	private final int page;

	/**
	 * The page size.
	 */
	private final int pageSize;

	/**
	 * Create page.
	 *
	 * @param page     The page.
	 * @param pageSize The page size.
	 */
	private Page(int page, int pageSize) {
		this.page = gte(1, page, "Page must starts with 1");
		this.pageSize = gte(1, pageSize, "Page size must be at least 1");
	}

	/**
	 * Get {@link #page}
	 *
	 * @return {@link #page}
	 */
	public int getPage() {
		return page;
	}

	/**
	 * Get {@link #pageSize}
	 *
	 * @return {@link #pageSize}
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * Get the offset: the number of first element in all results.
	 *
	 * @return The offset.
	 */
	public int getOffset() {
		return (page - 1) * pageSize;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this)
				.append("page", page)
				.append("pageSize", pageSize)
				.build();
	}

	@Override
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}

		if (o instanceof Page) {
			Page s = (Page) o;
			return Objects.equals(page, s.page) && Objects.equals(pageSize, s.pageSize);
		}

		return false;
	}

	@Override
	public int hashCode() {
		return Objects.hash(page, pageSize);
	}
}
