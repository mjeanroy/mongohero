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

package com.github.mjeanroy.mongohero.tests.junit;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Objects;

public final class MongoDbContainerDescriptor {

	/**
	 * The mongodb hostname to query.
	 */
	private final String host;

	/**
	 * The port to bind to.
	 */
	private final int port;

	/**
	 * If SSL is enabled on this mongo instance.
	 */
	private final boolean ssl;

	/**
	 * The replicaset name.
	 */
	private final String replicaSet;

	/**
	 * The MongoDB container descriptor.
	 *
	 * @param host MongoDB host.
	 * @param port MongoDB port.
	 * @param ssl SSL flag on this MongoDB instance.
	 * @param replicaSet The replicaset name.
	 */
	MongoDbContainerDescriptor(String host, int port, boolean ssl, String replicaSet) {
		this.host = host;
		this.port = port;
		this.ssl = ssl;
		this.replicaSet = replicaSet;
	}

	/**
	 * Get {@link #host}
	 *
	 * @return {@link #host}
	 */
	public String getHost() {
		return host;
	}

	/**
	 * Get {@link #port}
	 *
	 * @return {@link #port}
	 */
	public int getPort() {
		return port;
	}

	/**
	 * Get {@link #ssl}
	 *
	 * @return {@link #ssl}
	 */
	public boolean isSsl() {
		return ssl;
	}

	/**
	 * Get {@link #replicaSet}
	 *
	 * @return {@link #replicaSet}
	 */
	public String getReplicaSet() {
		return replicaSet;
	}

	@Override
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}

		if (o instanceof MongoDbContainerDescriptor) {
			MongoDbContainerDescriptor c = (MongoDbContainerDescriptor) o;
			return Objects.equals(host, c.host)
					&& Objects.equals(port, c.port)
					&& Objects.equals(ssl, c.ssl)
					&& Objects.equals(replicaSet, c.replicaSet);
		}

		return false;
	}

	@Override
	public int hashCode() {
		return Objects.hash(host, port, ssl, replicaSet);
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this)
				.append("host", host)
				.append("port", port)
				.append("ssl", ssl)
				.append("replicaSet", replicaSet)
				.build();
	}
}
