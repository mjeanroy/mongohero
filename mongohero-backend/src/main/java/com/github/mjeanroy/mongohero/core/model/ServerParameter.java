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

package com.github.mjeanroy.mongohero.core.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Objects;

public final class ServerParameter {

    /**
     * The parameter name.
     */
    private final String name;

    /**
     * The parameter value.
     */
    private final Object value;

    public ServerParameter(String name, Object value) {
        this.name = name;
        this.value = value;
    }

    /**
     * Get {@link #name}
     *
     * @return {@link #name}
     */
    public String getName() {
        return name;
    }

    /**
     * Get {@link #value}
     *
     * @return {@link #value}
     */
    public Object getValue() {
        return value;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("name", name)
                .append("value", value)
                .build();
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (o instanceof ServerParameter) {
            ServerParameter p = (ServerParameter) o;
            return Objects.equals(name, p.name) && Objects.equals(value, p.value);
        }

        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, value);
    }
}
