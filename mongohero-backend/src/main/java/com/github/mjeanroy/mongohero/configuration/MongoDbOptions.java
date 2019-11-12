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

package com.github.mjeanroy.mongohero.configuration;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.boot.context.properties.bind.DefaultValue;

import java.util.Objects;

@ConstructorBinding
public final class MongoDbOptions {

	private final String connectionMode;
	private final String readPreference;
	private final String readConcernLevel;
	private final int maxPoolSize;
	private final int minPoolSize;
	private final int connectTimeoutMs;
	private final int readTimeoutMs;
	private final int maxWaitQueueSize;

	public MongoDbOptions(
			@DefaultValue("single") String connectionMode,
			@DefaultValue("") String readPreference,
			@DefaultValue("") String readConcernLevel,
			@DefaultValue("100") int maxPoolSize,
			@DefaultValue("0") int minPoolSize,
			@DefaultValue("-1") int connectTimeoutMs,
			@DefaultValue("-1") int readTimeoutMs,
			@DefaultValue("-1") int maxWaitQueueSize) {

		this.connectionMode = connectionMode;
		this.readPreference = readPreference;
		this.readConcernLevel = readConcernLevel;
		this.maxPoolSize = maxPoolSize;
		this.minPoolSize = minPoolSize;
		this.connectTimeoutMs = connectTimeoutMs;
		this.readTimeoutMs = readTimeoutMs;
		this.maxWaitQueueSize = maxWaitQueueSize;
	}

	/**
	 * Get {@link #connectionMode}
	 *
	 * @return {@link #connectionMode}
	 */
	public String getConnectionMode() {
		return connectionMode;
	}

	/**
	 * Get {@link #readConcernLevel}
	 *
	 * @return {@link #readConcernLevel}
	 */
	String getReadConcernLevel() {
		return readConcernLevel;
	}

	/**
	 * Get {@link #readPreference}
	 *
	 * @return {@link #readPreference}
	 */
	String getReadPreference() {
		return readPreference;
	}

	/**
	 * Get {@link #maxPoolSize}
	 *
	 * @return {@link #maxPoolSize}
	 */
	int getMaxPoolSize() {
		return maxPoolSize;
	}

	/**
	 * Get {@link #minPoolSize}
	 *
	 * @return {@link #minPoolSize}
	 */
	int getMinPoolSize() {
		return minPoolSize;
	}

	/**
	 * Get {@link #connectTimeoutMs}
	 *
	 * @return {@link #connectTimeoutMs}
	 */
	int getConnectTimeoutMs() {
		return connectTimeoutMs;
	}

	/**
	 * Get {@link #readTimeoutMs}
	 *
	 * @return {@link #readTimeoutMs}
	 */
	int getReadTimeoutMs() {
		return readTimeoutMs;
	}

	/**
	 * Get {@link #maxWaitQueueSize}
	 *
	 * @return {@link #maxWaitQueueSize}
	 */
	int getMaxWaitQueueSize() {
		return maxWaitQueueSize;
	}

	@Override
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}

		if (o instanceof MongoDbOptions) {
			MongoDbOptions p = (MongoDbOptions) o;
			return Objects.equals(connectionMode, p.connectionMode)
					&& Objects.equals(readPreference, p.readPreference)
					&& Objects.equals(maxPoolSize, p.maxPoolSize)
					&& Objects.equals(minPoolSize, p.minPoolSize)
					&& Objects.equals(connectTimeoutMs, p.connectTimeoutMs)
					&& Objects.equals(readTimeoutMs, p.readTimeoutMs)
					&& Objects.equals(maxWaitQueueSize, p.maxWaitQueueSize);
		}

		return false;
	}

	@Override
	public int hashCode() {
		return Objects.hash(
				connectionMode,
				readPreference,
				maxPoolSize,
				minPoolSize,
				connectTimeoutMs,
				readTimeoutMs,
				maxWaitQueueSize
		);
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this)
				.append("connectionMode", connectionMode)
				.append("readPreference", readPreference)
				.append("maxPoolSize", maxPoolSize)
				.append("minPoolSize", minPoolSize)
				.append("connectTimeoutMs", connectTimeoutMs)
				.append("readTimeoutMs", readTimeoutMs)
				.append("maxWaitQueueSize", maxWaitQueueSize)
				.build();
	}
}
