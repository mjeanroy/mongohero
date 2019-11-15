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

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.boot.context.properties.bind.DefaultValue;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@ConstructorBinding
@ConfigurationProperties("mongodb")
public final class MongoDbProperties {

	/**
	 * MongoDB host.
	 *
	 * To configure a list of host, simply specify them using a comma to separate host entries (note that port is optional and
	 * default will {@code 27017}), such as:
	 *
	 * <ul>
	 *   <li>{@code "localhost:27017,localhost27018}</li>
	 *   <li>{@code "host1.domain.com,host2.domain.com}</li>
	 * </ul>
	 */
	private final String host;

	/**
	 * The replica set name (optional).
	 */
	private final String replicaSet;

	/**
	 * Username.
	 */
	private final String user;

	/**
	 * Password.
	 */
	private final String password;

	/**
	 * Authentication Database, default is {@code "admin"}.
	 */
	private final String database;

	/**
	 * SSL Flag to enable encrypted communication.
	 */
	private final boolean ssl;

	/**
	 * Various MongoDB option.
	 */
	private final MongoDbOptions options;

	public MongoDbProperties(
			@DefaultValue("localhost:27017") String host,
			String replicaSet,
			String user,
			String password,
			@DefaultValue("admin") String database,
			@DefaultValue("false") boolean ssl,
			MongoDbOptions options) {

		this.host = host;
		this.replicaSet = replicaSet;
		this.user = user;
		this.database = database;
		this.password = password;
		this.ssl = ssl;
		this.options = options;
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
	 * Get {@link #replicaSet}
	 *
	 * @return {@link #replicaSet}
	 */
	String getReplicaSet() {
		return replicaSet;
	}

	/**
	 * Get structured host list.
	 *
	 * @return The structured list.
	 */
	List<MongoDbHost> getHosts() {
		return Arrays.stream(host.split(","))
				.map(String::trim)
				.filter(StringUtils::isNotEmpty)
				.map(this::toMongoDbHost)
				.collect(Collectors.toList());
	}

	private MongoDbHost toMongoDbHost(String host) {
		String[] parts = host.split(",", 2);
		String hostname = parts[0].trim();
		int port = parts.length == 2 ? Integer.parseInt(parts[1].trim()) : 27017;
		return new MongoDbHost(hostname, port);
	}

	/**
	 * Get {@link #host}
	 *
	 * @return {@link #host}
	 */
	String getUser() {
		return user;
	}

	/**
	 * Get {@link #host}
	 *
	 * @return {@link #host}
	 */
	String getPassword() {
		return password;
	}

	/**
	 * Get {@link #host}
	 *
	 * @return {@link #host}
	 */
	String getDatabase() {
		return database;
	}

	/**
	 * Get {@link #host}
	 *
	 * @return {@link #host}
	 */
	boolean isSsl() {
		return ssl;
	}

	/**
	 * Get {@link #host}
	 *
	 * @return {@link #host}
	 */
	MongoDbOptions getOptions() {
		return options;
	}

	@Override
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}

		if (o instanceof MongoDbProperties) {
			MongoDbProperties p = (MongoDbProperties) o;
			return Objects.equals(host, p.host)
					&& Objects.equals(user, p.user)
					&& Objects.equals(password, p.password)
					&& Objects.equals(ssl, p.ssl)
					&& Objects.equals(replicaSet, p.replicaSet)
					&& Objects.equals(options, p.options);
		}

		return false;
	}

	@Override
	public int hashCode() {
		return Objects.hash(host, replicaSet, user, password, ssl, options);
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this)
				.append("host", host)
				.append("replicaSet", replicaSet)
				.append("user", user)
				.append("password", "***")
				.append("ssl", ssl)
				.append("options", options)
				.build();
	}
}
