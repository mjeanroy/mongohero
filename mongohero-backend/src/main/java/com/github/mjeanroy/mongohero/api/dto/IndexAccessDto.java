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

package com.github.mjeanroy.mongohero.api.dto;

import java.util.Date;

public class IndexAccessDto extends AbstractDto {

	/**
	 * The number of operations that used the index.
	 */
	private long ops;

	/**
	 * The time from which MongoDB gathered the statistics.
	 */
	private Date since;

	/**
	 * Get {@link #ops}
	 *
	 * @return {@link #ops}
	 */
	public long getOps() {
		return ops;
	}

	/**
	 * Set {@link #ops}
	 *
	 * @param ops {@link #ops}
	 */
	public void setOps(long ops) {
		this.ops = ops;
	}

	/**
	 * Get {@link #since}
	 *
	 * @return {@link #since}
	 */
	public Date getSince() {
		return since;
	}

	/**
	 * Set {@link #since}
	 *
	 * @param since {@link #since}
	 */
	public void setSince(Date since) {
		this.since = since;
	}
}
