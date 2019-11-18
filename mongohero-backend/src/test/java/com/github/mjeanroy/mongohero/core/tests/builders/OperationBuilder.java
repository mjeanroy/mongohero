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

import com.github.mjeanroy.mongohero.core.model.Operation;

import static com.github.mjeanroy.mongohero.core.tests.ReflectionTestUtils.instantiate;
import static com.github.mjeanroy.mongohero.core.tests.ReflectionTestUtils.writePrivateField;

/**
 * Builder for {@link Operation} to use in unit tests.
 */
public class OperationBuilder {

	/**
	 * A string with information about the type of client which made the request.
	 *
	 * @see Operation#getAppName()
	 */
	private String appName;

	/**
	 * Set {@link #appName}
	 *
	 * @param appName New {@link #appName}
	 * @return The builder.
	 */
	public OperationBuilder withAppName(String appName) {
		this.appName = appName;
		return this;
	}

	/**
	 * Build {@link Operation} instance.
	 *
	 * @return The new {@link Operation}
	 */
	public Operation build() {
		Operation operation = instantiate(Operation.class);
		writePrivateField(operation, "appName", appName);
		return operation;
	}
}
