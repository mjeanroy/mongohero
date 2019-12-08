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

package com.github.mjeanroy.mongohero.core.tests.builders;

import com.github.mjeanroy.mongohero.core.model.IndexAccess;
import com.github.mjeanroy.mongohero.core.model.IndexStat;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import static com.github.mjeanroy.mongohero.core.tests.ReflectionTestUtils.instantiate;
import static com.github.mjeanroy.mongohero.core.tests.ReflectionTestUtils.writePrivateField;

/**
 * Builder for {@link IndexAccess}.
 */
public class IndexStatBuilder {

	/**
	 * Index name.
	 *
	 * @see IndexStat#getName()
	 */
	private String name;

	/**
	 * Index key specification.
	 *
	 * @see IndexStat#getKey()
	 */
	private final Map<String, Number> key;

	/**
	 * Statistics on the index use
	 *
	 * @see IndexStat#getAccesses()
	 */
	private IndexAccess accesses;

	public IndexStatBuilder() {
		this.key = new LinkedHashMap<>();
	}

	/**
	 * Update {@link #name}
	 *
	 * @param name New {@link #name}
	 * @return The builder.
	 */
	public IndexStatBuilder withName(String name) {
		this.name = name;
		return this;
	}

	/**
	 * Update {@link #key}
	 *
	 * @param key New {@link #key}
	 * @return The builder.
	 */
	public IndexStatBuilder withKey(Map<String, Number> key) {
		this.key.clear();
		this.key.putAll(key);
		return this;
	}

	/**
	 * Update {@link #accesses} with given {@code "ops"} and {@code "since"} value.
	 *
	 * @param ops   New {@link IndexAccess#getOps()}
	 * @param since New {@link IndexAccess#getSince()}
	 * @return The builder.
	 */
	public IndexStatBuilder withAccesses(long ops, Date since) {
		this.accesses = new IndexAccessBuilder().withOps(ops).withSince(since).build();
		return this;
	}

	/**
	 * Build {@link IndexAccess} instance.
	 *
	 * @return The created instance.
	 */
	public IndexStat build() {
		IndexStat indexStat = instantiate(IndexStat.class);
		writePrivateField(indexStat, "name", name);
		writePrivateField(indexStat, "key", new LinkedHashMap<>(key));
		writePrivateField(indexStat, "accesses", accesses);
		return indexStat;
	}
}
