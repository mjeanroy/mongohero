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
			String connectionMode,
			String readPreference,
			String readConcernLevel,
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
	String getConnectionMode() {
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

	/**
	 * Create builder from this options.
	 *
	 * @return The builder.
	 */
	Builder toBuilder() {
		return new Builder(this);
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

	static class Builder {
		/**
		 * The MongoDB Client Connection Mode.
		 *
		 * @see MongoDbOptions#getConnectionMode()
		 */
		private String connectionMode;

		/**
		 * The MongoDB Client Read Preference.
		 *
		 * @see MongoDbOptions#getReadPreference()
		 */
		private String readPreference;

		/**
		 * The MongoDB Client ReadConcern Level.
		 *
		 * @see MongoDbOptions#getReadConcernLevel()
		 */
		private String readConcernLevel;

		/**
		 * The MongoDB Client Maximum Pool Size.
		 *
		 * @see MongoDbOptions#getMaxPoolSize()
		 */
		private int maxPoolSize;

		/**
		 * The MongoDB Client Minimum Pool Size.
		 *
		 * @see MongoDbOptions#getMinPoolSize()
		 */
		private int minPoolSize;

		/**
		 * The MongoDB Client Connection Timeout (ms).
		 *
		 * @see MongoDbOptions#getConnectTimeoutMs()
		 */
		private int connectTimeoutMs;

		/**
		 * The MongoDB Client Read Timeout (ms).
		 *
		 * @see MongoDbOptions#getReadTimeoutMs()
		 */
		private int readTimeoutMs;

		/**
		 * The MongoDB Client Maximum "Wait Queue" Size.
		 *
		 * @see MongoDbOptions#getMaxWaitQueueSize()
		 */
		private int maxWaitQueueSize;

		/**
		 * Create builder from given MongoDB Options.
		 *
		 * @param options Existing Options.
		 */
		Builder(MongoDbOptions options) {
			this.connectionMode = options.getConnectionMode();
			this.readPreference = options.getReadPreference();
			this.readConcernLevel = options.getReadConcernLevel();
			this.maxPoolSize = options.getMaxPoolSize();
			this.minPoolSize = options.getMinPoolSize();
			this.connectTimeoutMs = options.getConnectTimeoutMs();
			this.readTimeoutMs = options.getReadTimeoutMs();
			this.maxWaitQueueSize = options.getMaxWaitQueueSize();
		}

		/**
		 * Update {@link #connectionMode}
		 *
		 * @param connectionMode New {@link #connectionMode}
		 * @return The builder.
		 */
		Builder withConnectionMode(String connectionMode) {
			this.connectionMode = connectionMode;
			return this;
		}

		/**
		 * Update {@link #readPreference}
		 *
		 * @param readPreference New {@link #readPreference}
		 * @return The builder.
		 */
		Builder withReadPrefernce(String readPreference) {
			this.readPreference = readPreference;
			return this;
		}

		/**
		 * Update {@link #readConcernLevel}
		 *
		 * @param readConcernLevel New {@link #readConcernLevel}
		 * @return The builder.
		 */
		Builder withReadConcernLevel(String readConcernLevel) {
			this.readConcernLevel = readConcernLevel;
			return this;
		}

		/**
		 * Update {@link #maxPoolSize}
		 *
		 * @param maxPoolSize New {@link #maxPoolSize}
		 * @return The builder.
		 */
		Builder withMaxPoolSize(int maxPoolSize) {
			this.maxPoolSize = maxPoolSize;
			return this;
		}

		/**
		 * Update {@link #minPoolSize}
		 *
		 * @param minPoolSize New {@link #minPoolSize}
		 * @return The builder.
		 */
		Builder withMinPoolSize(int minPoolSize) {
			this.minPoolSize = minPoolSize;
			return this;
		}

		/**
		 * Update {@link #connectTimeoutMs}
		 *
		 * @param connectTimeoutMs New {@link #connectTimeoutMs}
		 * @return The builder.
		 */
		Builder withConnectTimeoutMs(int connectTimeoutMs) {
			this.connectTimeoutMs = connectTimeoutMs;
			return this;
		}

		/**
		 * Update {@link #readTimeoutMs}
		 *
		 * @param readTimeoutMs New {@link #readTimeoutMs}
		 * @return The builder.
		 */
		Builder withReadTimeoutMs(int readTimeoutMs) {
			this.readTimeoutMs = readTimeoutMs;
			return this;
		}

		/**
		 * Update {@link #maxWaitQueueSize}
		 *
		 * @param maxWaitQueueSize New {@link #maxWaitQueueSize}
		 * @return The builder.
		 */
		Builder withMaxWaitQueueSize(int maxWaitQueueSize) {
			this.maxWaitQueueSize = maxWaitQueueSize;
			return this;
		}

		/**
		 * Create immutable MongoDB Client Options.
		 *
		 * @return Options.
		 */
		MongoDbOptions build() {
			return new MongoDbOptions(
					connectionMode,
					readPreference,
					readConcernLevel,
					maxPoolSize,
					minPoolSize,
					connectTimeoutMs,
					readTimeoutMs,
					maxWaitQueueSize
			);
		}
	}
}
