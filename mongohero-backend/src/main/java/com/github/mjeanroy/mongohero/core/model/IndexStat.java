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

package com.github.mjeanroy.mongohero.core.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Map;

/**
 * Statistics regarding the use of an index.
 *
 * @see <a href="https://docs.mongodb.com/manual/reference/operator/aggregation/indexStats/">https://docs.mongodb.com/manual/reference/operator/aggregation/indexStats/</a>
 */
public class IndexStat {

	/**
	 * Index name.
	 */
	private String name;

	/**
	 * Index key specification.
	 */
	private Map<String, Number> key;

	/**
	 * Statistics on the index use:
	 */
	private IndexAccess accesses;

	IndexStat() {
	}

	/**
	 * Get {@link #name}
	 *
	 * @return Get {@link #name}
	 */
	public String getName() {
		return name;
	}

	/**
	 * Get {@link #key}
	 *
	 * @return Get {@link #key}
	 */
	public Map<String, Number> getKey() {
		return key;
	}

	/**
	 * Get {@link #accesses}
	 *
	 * @return Get {@link #accesses}
	 */
	public IndexAccess getAccesses() {
		return accesses;
	}

	/**
	 * Merge this index statistics with other one.
	 *
	 * @param other The other one.
	 * @return THe result.
	 */
	public IndexStat merge(IndexStat other) {
		IndexStat indexStat = new IndexStat();
		indexStat.name = name;
		indexStat.key = key;
		indexStat.accesses = accesses.merge(other.accesses);
		return indexStat;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this)
				.append("name", name)
				.append("key", key)
				.append("accesses", accesses)
				.build();
	}
}
