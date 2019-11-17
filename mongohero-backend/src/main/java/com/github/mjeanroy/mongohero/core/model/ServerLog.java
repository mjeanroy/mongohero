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

import java.util.List;

/**
 * The server logs as returned by the {@code "getLog"} command.
 *
 * @see <a href="https://docs.mongodb.com/manual/reference/command/getLog/">https://docs.mongodb.com/manual/reference/command/getLog/</a>
 */
public class ServerLog {

	/**
	 * Number of log events.
	 */
	private long totalLinesWritten;

	/**
	 * Array of log events.
	 */
	private List<String> log;

	ServerLog() {
	}

	/**
	 * Get {@link #totalLinesWritten}
	 *
	 * @return {@link #totalLinesWritten}
	 */
	public long getTotalLinesWritten() {
		return totalLinesWritten;
	}

	/**
	 * Get {@link #log}
	 *
	 * @return {@link #log}
	 */
	public List<String> getLog() {
		return log;
	}
}
