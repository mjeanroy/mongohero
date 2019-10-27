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

package com.github.mjeanroy.mongohero.api.core;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Objects;

public class Sort {

    static Sort parse(String param) {
        char firstChar = param.charAt(0);
        Order order = firstChar == '-' ? Order.DESC : Order.ASC;
        String name = firstChar == '-' || firstChar == '+' ? param.substring(1) : param;
        return new Sort(name, order);
    }

    public enum Order {
        ASC, DESC;
    }

    private final String name;
    private final Order order;

    Sort(String name, Order order) {
        this.name = name;
        this.order = order;
    }

    public String getName() {
        return name;
    }

    public Order getOrder() {
        return order;
    }

    public boolean isAsc() {
        return order == Order.ASC;
    }

    public boolean isDesc() {
        return order == Order.DESC;
    }

    public int toInt() {
        return isAsc() ? 1 : -1;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("name", name)
                .append("order", order)
                .build();
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (o instanceof Sort) {
            Sort s = (Sort) o;
            return Objects.equals(name, s.name) && Objects.equals(order, s.order);
        }

        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, order);
    }
}
