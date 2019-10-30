package com.github.mjeanroy.mongohero.core.query;

import java.util.stream.Stream;

public class PageResult<T> {

    public static <T> PageResult<T> of(Stream<T> results, Page page, Sort sort, long total) {
        return new PageResult<>(
                results,
                page,
                sort,
                total
        );
    }

    private final Stream<T> results;
    private final Page page;
    private final Sort sort;
    private final long total;

    private PageResult(Stream<T> results, Page page, Sort sort, long total) {
        this.results = results;
        this.page = page;
        this.sort = sort;
        this.total = total;
    }

    public Stream<T> getResults() {
        return results;
    }

    public Page getPage() {
        return page;
    }

    public Sort getSort() {
        return sort;
    }

    public long getTotal() {
        return total;
    }

    public int page() {
        return page.getPage();
    }

    public int pageSize() {
        return page.getPageSize();
    }
}
