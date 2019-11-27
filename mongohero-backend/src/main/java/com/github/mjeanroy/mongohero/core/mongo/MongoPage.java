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

package com.github.mjeanroy.mongohero.core.mongo;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.bson.Document;

import java.util.Iterator;
import java.util.Objects;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.stream.Stream;

import static com.github.mjeanroy.mongohero.commons.PreConditions.gte;
import static com.github.mjeanroy.mongohero.commons.PreConditions.notNull;
import static com.github.mjeanroy.mongohero.commons.Streams.toStream;

/**
 * A Mongo Page containing results for a given page size that can be iterated
 * and streamed.
 */
public final class MongoPage implements Iterable<Document> {

	/**
	 * Create mongo page.
	 *
	 * @param results Result page.
	 * @param total   Total number of results across all pages.
	 * @return The mongo page.
	 */
	public static MongoPage of(Stream<Document> results, long total) {
		return new MongoPage(results, total);
	}

	/**
	 * The page results.
	 */
	private final Stream<Document> results;

	/**
	 * The total number of results across all pages.
	 */
	private final long total;

	/**
	 * Create mongo page.
	 *
	 * @param results Result page.
	 * @param total   Total number of results across all pages.
	 */
	private MongoPage(Stream<Document> results, long total) {
		this.results = notNull(results, "Results must be defined");
		this.total = gte(0, total, "Total must be positive");
	}

	/**
	 * Get {@link #results}
	 *
	 * @return {@link #results}
	 */
	public Stream<Document> getResults() {
		return results;
	}

	/**
	 * Get {@link #total}
	 *
	 * @return {@link #total}
	 */
	public long getTotal() {
		return total;
	}

	@Override
	public Iterator<Document> iterator() {
		return results.iterator();
	}

	@Override
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}

		if (o instanceof MongoPage) {
			MongoPage p = (MongoPage) o;
			return Objects.equals(results, p.results) && Objects.equals(total, p.total);
		}

		return false;
	}

	@Override
	public int hashCode() {
		return Objects.hash(results, total);
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this)
				.append("results", results)
				.append("total", total)
				.build();
	}

	@Override
	public void forEach(Consumer<? super Document> action) {
		results.forEach(action);
	}

	@Override
	public Spliterator<Document> spliterator() {
		return results.spliterator();
	}

	/**
	 * Returns a sequential {@code Stream} with this page content as its source.
	 *
	 * @return A sequential {@code Stream} over the elements in this page.
	 */
	public Stream<Document> stream() {
		return toStream(this);
	}
}
