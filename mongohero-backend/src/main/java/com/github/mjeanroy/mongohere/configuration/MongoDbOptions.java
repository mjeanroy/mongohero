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

package com.github.mjeanroy.mongohere.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.boot.context.properties.bind.DefaultValue;

import java.util.Objects;

@ConstructorBinding
@ConfigurationProperties("mongodb")
public final class MongoDbOptions {

    private final int maxPoolSize;
    private final int minPoolSize;

    public MongoDbOptions(
            @DefaultValue("100") int maxPoolSize,
            @DefaultValue("0") int minPoolSize) {

        this.maxPoolSize = maxPoolSize;
        this.minPoolSize = minPoolSize;
    }

    public int getMaxPoolSize() {
        return maxPoolSize;
    }

    public int getMinPoolSize() {
        return minPoolSize;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (o instanceof MongoDbOptions) {
            MongoDbOptions p = (MongoDbOptions) o;
            return Objects.equals(maxPoolSize, p.maxPoolSize)
                    && Objects.equals(minPoolSize, p.minPoolSize);
        }

        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(maxPoolSize, minPoolSize);
    }
}
