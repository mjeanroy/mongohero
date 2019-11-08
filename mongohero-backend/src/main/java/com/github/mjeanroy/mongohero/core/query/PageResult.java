/**
 * The MIT License (MIT)
 * <p>
 * Copyright (c) 2019 Mickael Jeanroy
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * <p>
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

	public static <T> PageResult<T> of(Stream<T> results, Page page, Sort sort, long total) {
		return new PageResult<>(
				results,
				page,
				sort,
				total
		);
	}

	private final Stream<T> results;
	private final Page page;
	private final Sort sort;
	private final long total;

	private PageResult(Stream<T> results, Page page, Sort sort, long total) {
		this.results = results;
		this.page = page;
		this.sort = sort;
		this.total = total;
	}

	public Stream<T> getResults() {
		return results;
	}

	public Page getPage() {
		return page;
	}

	public Sort getSort() {
		return sort;
	}

	public long getTotal() {
		return total;
	}

	public int page() {
		return page.getPage();
	}

	public int pageSize() {
		return page.getPageSize();
	}
}
