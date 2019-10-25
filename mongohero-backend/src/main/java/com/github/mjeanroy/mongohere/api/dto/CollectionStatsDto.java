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

package com.github.mjeanroy.mongohere.api.dto;

import java.util.List;

public class CollectionStatsDto {

    private String ns;
    private double size;
    private long count;
    private double avgObjSize;
    private double storageSize;
    private boolean capped;
    private long nindexes;
    private double totalIndexSize;
    private List<IndexSizeDto> indexSizes;

    public String getNs() {
        return ns;
    }

    public void setNs(String ns) {
        this.ns = ns;
    }

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public double getAvgObjSize() {
        return avgObjSize;
    }

    public void setAvgObjSize(double avgObjSize) {
        this.avgObjSize = avgObjSize;
    }

    public double getStorageSize() {
        return storageSize;
    }

    public void setStorageSize(double storageSize) {
        this.storageSize = storageSize;
    }

    public boolean isCapped() {
        return capped;
    }

    public void setCapped(boolean capped) {
        this.capped = capped;
    }

    public long getNindexes() {
        return nindexes;
    }

    public void setNindexes(long nindexes) {
        this.nindexes = nindexes;
    }

    public double getTotalIndexSize() {
        return totalIndexSize;
    }

    public void setTotalIndexSize(double totalIndexSize) {
        this.totalIndexSize = totalIndexSize;
    }

    public List<IndexSizeDto> getIndexSizes() {
        return indexSizes;
    }

    public void setIndexSizes(List<IndexSizeDto> indexSizes) {
        this.indexSizes = indexSizes;
    }
}
