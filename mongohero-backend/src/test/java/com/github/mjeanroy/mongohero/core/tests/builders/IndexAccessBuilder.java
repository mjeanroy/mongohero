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

import java.util.Date;

import static com.github.mjeanroy.mongohero.core.tests.ReflectionTestUtils.instantiate;
import static com.github.mjeanroy.mongohero.core.tests.ReflectionTestUtils.writePrivateField;

/**
 * Builder for {@link IndexAccess}.
 */
public class IndexAccessBuilder {

	/**
	 * Number of operations that used the index.
	 *
	 * @see IndexAccess#getOps()
	 */
	private long ops;

	/**
	 * Time from which MongoDB gathered the statistics.
	 *
	 * @see IndexAccess#getSince()
	 */
	private Date since;

	/**
	 * Update {@link #ops}
	 *
	 * @param ops New {@link #ops}
	 * @return The builder.
	 */
	public IndexAccessBuilder withOps(long ops) {
		this.ops = ops;
		return this;
	}

	/**
	 * Update {@link #since}
	 *
	 * @param since New {@link #since}
	 * @return The builder.
	 */
	public IndexAccessBuilder withSince(Date since) {
		this.since = since;
		return this;
	}

	/**
	 * Build {@link IndexAccess} instance.
	 *
	 * @return The created instance.
	 */
	public IndexAccess build() {
		IndexAccess indexAccess = instantiate(IndexAccess.class);
		writePrivateField(indexAccess, "ops", ops);
		writePrivateField(indexAccess, "since", since);
		return indexAccess;
	}
}
