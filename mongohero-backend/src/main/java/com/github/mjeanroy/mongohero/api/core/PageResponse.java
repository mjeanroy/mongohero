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

import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.Consumer;

public final class PageResponse<T> implements Iterable<T> {

    public static <T> PageResponse<T> of(Iterable<T> body, int page, int pageSize, long total) {
        return new PageResponse<>(
                body,
                page,
                pageSize,
                total
        );
    }

    private final Iterable<T> body;
    private final int page;
    private final int pageSize;
    private final long total;

    private PageResponse(Iterable<T> body, int page, int pageSize, long total) {
        this.body = body;
        this.page = page;
        this.pageSize = pageSize;
        this.total = total;
    }

    public long getTotal() {
        return total;
    }

    public Iterable<T> getBody() {
        return body;
    }

    public int getPage() {
        return page;
    }

    public int getPageSize() {
        return pageSize;
    }

    @Override
    public Iterator<T> iterator() {
        return body.iterator();
    }

    @Override
    public void forEach(Consumer<? super T> action) {
        body.forEach(action);
    }

    @Override
    public Spliterator<T> spliterator() {
        return body.spliterator();
    }
}
