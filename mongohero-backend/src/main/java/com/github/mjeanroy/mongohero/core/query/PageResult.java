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

import java.util.stream.Stream;

public class PageResult<T> {

	/**
	 * Create the page result.
	 *
	 * @param results Result set.
	 * @param page    The page being returned.
	 * @param sort    The sort being applied.
	 * @param total   The total number of results.
	 * @param <T>     Type of elements in the page results.
	 * @return The page result.
	 */
	public static <T> PageResult<T> of(Stream<T> results, Page page, Sort sort, long total) {
		return new PageResult<>(
				results,
				page,
				sort,
				total
		);
	}

	/**
	 * The result set.
	 */
	private final Stream<T> results;

	/**
	 * The page being returned.
	 */
	private final Page page;

	/**
	 * The sort being applied.
	 */
	private final Sort sort;

	/**
	 * The total number of results.
	 */
	private final long total;

	private PageResult(Stream<T> results, Page page, Sort sort, long total) {
		this.results = results;
		this.page = page;
		this.sort = sort;
		this.total = total;
	}

	/**
	 * Get {@link #results}
	 *
	 * @return {@link #results}
	 */
	public Stream<T> getResults() {
		return results;
	}

	/**
	 * Get {@link #page}
	 *
	 * @return {@link #page}
	 */
	public Page getPage() {
		return page;
	}

	/**
	 * Get {@link #sort}
	 *
	 * @return {@link #sort}
	 */
	public Sort getSort() {
		return sort;
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
	 * Get page number.
	 *
	 * @return The page number.
	 */
	public int page() {
		return page.getPage();
	}

	/**
	 * Get the page size.
	 *
	 * @return The page size.
	 */
	public int pageSize() {
		return page.getPageSize();
	}
}
