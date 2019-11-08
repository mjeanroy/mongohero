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

package com.github.mjeanroy.mongohero.core.model;

public class Database {

	/**
	 * Database name.
	 */
	private String name;

	/**
	 * Total size of the database files on disk in bytes.
	 */
	private double sizeOnDisk;

	/**
	 * Field specifying whether the database has any data.
	 */
	private boolean empty;

	/**
	 * Database stats.
	 *
	 * @see <a href="https://docs.mongodb.com/manual/reference/command/dbStats/">https://docs.mongodb.com/manual/reference/command/dbStats/</a>
	 */
	private DatabaseStats stats;

	Database() {
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
	 * Get {@link #sizeOnDisk}
	 *
	 * @return {@link #sizeOnDisk}
	 */
	public double getSizeOnDisk() {
		return sizeOnDisk;
	}

	/**
	 * Get {@link #empty}
	 *
	 * @return {@link #empty}
	 */
	public boolean isEmpty() {
		return empty;
	}

	/**
	 * Get {@link #stats}
	 *
	 * @return {@link #stats}
	 */
	public DatabaseStats getStats() {
		return stats;
	}
}
