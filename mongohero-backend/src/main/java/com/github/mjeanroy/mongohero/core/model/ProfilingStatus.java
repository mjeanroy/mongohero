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

/**
 * The database profiling status.
 *
 * @see <a href="https://docs.mongodb.com/manual/reference/command/profile/">https://docs.mongodb.com/manual/reference/command/profile/</a>
 */
public class ProfilingStatus {

	/**
	 * Indicates the current profiling level.
	 * The following profiler levels are available:
	 *
	 * <ul>
	 *     <li>{@code 0}: The profiler is off and does not collect any data. This is the default profiler level.</li>
	 *     <li>{@code 1}: The profiler collects data for operations that take longer than the value of slowms.</li>
	 *     <li>{@code 2}: The profiler collects data for all operations.</li>
	 * </ul>
	 */
	private int was;

	/**
	 * The slow operation time threshold, in milliseconds.
	 * Operations that run for longer than this threshold are considered slow.
	 *
	 * When logLevel is set to 0, MongoDB records slow operations to the diagnostic log at a rate
	 * determined by slowOpSampleRate.
	 * Starting in MongoDB 4.2, the secondaries of replica sets log all oplog entry messages that take
	 * longer than the slow operation threshold to apply regardless of the sample rate.
	 *
	 * At higher logLevel settings, all operations appear in the diagnostic log regardless of their latency
	 * with the following exception: the logging of slow oplog entry messages by the secondaries.
	 * The secondaries log only the slow oplog entries; increasing the logLevel does not log all oplog entries.
	 */
	private int slowms;

	ProfilingStatus() {
	}

	/**
	 * Get {@link #was}
	 *
	 * @return {@link #was}
	 */
	public int getWas() {
		return was;
	}

	/**
	 * Get {@link #slowms}
	 *
	 * @return {@link #slowms}
	 */
	public int getSlowms() {
		return slowms;
	}
}
