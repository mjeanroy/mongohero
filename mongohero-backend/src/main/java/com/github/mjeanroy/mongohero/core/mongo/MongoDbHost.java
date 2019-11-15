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

import java.util.Objects;

final class MongoDbHost {

	private final String host;
	private final int port;

	MongoDbHost(String host, int port) {
		this.host = host;
		this.port = port;
	}

	/**
	 * Get {@link #host}
	 *
	 * @return {@link #host}
	 */
	String getHost() {
		return host;
	}

	/**
	 * Get {@link #port}
	 *
	 * @return {@link #port}
	 */
	int getPort() {
		return port;
	}

	@Override
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}

		if (o instanceof MongoDbHost) {
			MongoDbHost p = (MongoDbHost) o;
			return Objects.equals(host, p.host)
					&& Objects.equals(port, p.port);
		}

		return false;
	}

	@Override
	public int hashCode() {
		return Objects.hash(host,  port);
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this)
				.append("host", host)
				.append("port", port)
				.build();
	}
}
