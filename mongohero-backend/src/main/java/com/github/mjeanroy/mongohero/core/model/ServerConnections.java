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

public class ServerConnections {

	/**
	 * The number of incoming connections from clients to the database server .
	 * This number includes the current shell session.
	 * Consider the value of connections.available to add more context to this datum.
	 *
	 * The value will include all incoming connections including any shell connections
	 * or connections from other servers, such as replica set members or mongos instances.
	 */
	private long current;

	/**
	 * The number of unused incoming connections available.
	 * Consider this value in combination with the value of connections.current to
	 * understand the connection load on the database, and the UNIX ulimit Settings document
	 * for more information about system thresholds on available connections.
	 */
	private long available;

	/**
	 * Count of all incoming connections created to the server.
	 * This number includes connections that have since closed.
	 */
	private long totalCreated;

	ServerConnections() {
	}

	/**
	 * Get {@link #current}
	 *
	 * @return {@link #current}
	 */
	public long getCurrent() {
		return current;
	}

	/**
	 * Get {@link #available}
	 *
	 * @return {@link #available}
	 */
	public long getAvailable() {
		return available;
	}

	/**
	 * Get {@link #totalCreated}
	 *
	 * @return {@link #totalCreated}
	 */
	public long getTotalCreated() {
		return totalCreated;
	}
}
