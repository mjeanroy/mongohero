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

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.boot.context.properties.bind.DefaultValue;

import java.util.Objects;

@ConstructorBinding
@ConfigurationProperties("mongodb")
public final class MongoDbProperties {

    private final String host;
    private final int port;
    private final String user;
    private final String password;
    private final boolean ssl;
    private final MongoDbOptions options;

    public MongoDbProperties(
            @DefaultValue("localhost") String host,
            @DefaultValue("27017") int port,
            String database,
            String user,
            @DefaultValue("") String password,
            @DefaultValue("false") boolean ssl,
            MongoDbOptions options) {

        this.host = host;
        this.port = port;
        this.user = user;
        this.password = password;
        this.ssl = ssl;
        this.options = options;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public boolean isSsl() {
        return ssl;
    }

    public MongoDbOptions getOptions() {
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
                    && Objects.equals(port, p.port)
                    && Objects.equals(user, p.user)
                    && Objects.equals(password, p.password)
                    && Objects.equals(ssl, p.ssl)
                    && Objects.equals(options, p.options);
        }

        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(host, port, user, password, ssl, options);
    }
}
