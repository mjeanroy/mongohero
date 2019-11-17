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

import com.github.mjeanroy.mongohero.core.model.ServerLog;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.github.mjeanroy.mongohero.core.tests.ReflectionTestUtils.instantiate;
import static com.github.mjeanroy.mongohero.core.tests.ReflectionTestUtils.writePrivateField;

/**
 * Builder for {@link ServerLog}, usable in unit tests.
 */
public class ServerLogBuilder {

	/**
	 * Log Content.
	 *
	 * @see ServerLog#getLog()
	 */
	private final List<String> log;

	/**
	 * Create builder.
	 */
	public ServerLogBuilder() {
		this.log = new ArrayList<>();
	}

	/**
	 * Add new line of logs.
	 *
	 * @param log    First line of log.
	 * @param others Other lines of logs.
	 * @return The builder.
	 */
	public ServerLogBuilder addLog(String log, String... others) {
		this.log.add(log);
		Collections.addAll(this.log, others);
		return this;
	}

	/**
	 * Build target object.
	 *
	 * @return New instance.
	 */
	public ServerLog build() {
		ServerLog output = instantiate(ServerLog.class);
		writePrivateField(output, "log", new ArrayList<>(log));
		return output;
	}
}
