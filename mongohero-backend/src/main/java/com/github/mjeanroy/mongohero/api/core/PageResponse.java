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

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Iterator;
import java.util.Objects;
import java.util.Spliterator;
import java.util.function.Consumer;

import static com.github.mjeanroy.mongohero.commons.PreConditions.gte;
import static com.github.mjeanroy.mongohero.commons.PreConditions.notNull;

/**
 * A page response that can be returned in any {@link org.springframework.web.bind.annotation.RestController} methods and
 * will serialized.
 *
 * A page response can be used in any pageable query.
 *
 * @param <T> Type of objects in the response body.
 */
public final class PageResponse<T> implements Iterable<T> {

	/**
	 * Create the page response.
	 *
	 * @param body     Response body.
	 * @param page     The page being returned.
	 * @param pageSize The page size.
	 * @param total    The total number of results.
	 * @param <T>      Type of elements in the response body.
	 * @return The page.
	 */
	public static <T> PageResponse<T> of(Iterable<T> body, int page, int pageSize, long total) {
		return new PageResponse<>(
				body,
				page,
				pageSize,
				total
		);
	}

	/**
	 * The response body.
	 */
	private final Iterable<T> body;

	/**
	 * The page being returned.
	 */
	private final int page;

	/**
	 * The page size (the response body size may be less than the page size).
	 */
	private final int pageSize;

	/**
	 * The total number of results.
	 */
	private final long total;

	/**
	 * Create page response.
	 *
	 * @param body     Response body.
	 * @param page     The page being returned.
	 * @param pageSize The page size.
	 * @param total    The total number of results.
	 */
	private PageResponse(Iterable<T> body, int page, int pageSize, long total) {
		this.body = notNull(body, "Response body must not be null");
		this.page = gte(1, page, "Page must start with 1");
		this.pageSize = gte(1, pageSize, "Page size must be at least 1");
		this.total = gte(0, total, "Total must be positive");
	}

	/**
	 * Get {@link #total}
	 *
	 * @return {@link #total}
	 */
	public long getTotal() {
		return total;
	}

	/**
	 * Get {@link #body}
	 *
	 * @return {@link #body}
	 */
	public Iterable<T> getBody() {
		return body;
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

	@Override
	public Iterator<T> iterator() {
		return body.iterator();
	}

	@Override
	public void forEach(Consumer<? super T> action) {
		body.forEach(action);
	}

	@Override
	public Spliterator<T> spliterator() {
		return body.spliterator();
	}

	@Override
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}

		if (o instanceof PageResponse) {
			PageResponse p = (PageResponse) o;
			return Objects.equals(body, p.body)
					&& Objects.equals(page, p.page)
					&& Objects.equals(pageSize, p.pageSize)
					&& Objects.equals(total, p.total);
		}

		return false;
	}

	@Override
	public int hashCode() {
		return Objects.hash(body, page, pageSize, total);
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this)
				.append("body", body)
				.append("page", page)
				.append("pageSize", pageSize)
				.append("total", total)
				.build();
	}
}
