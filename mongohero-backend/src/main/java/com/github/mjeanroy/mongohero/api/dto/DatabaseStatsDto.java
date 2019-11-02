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

package com.github.mjeanroy.mongohero.api.dto;

public class DatabaseStatsDto {

    private long collections;
    private long objects;
    private double dataSize;
    private double storageSize;
    private double indexSize;
    private long indexes;
    private long numExtents;

    public long getCollections() {
        return collections;
    }

    public void setCollections(long collections) {
        this.collections = collections;
    }

    public long getObjects() {
        return objects;
    }

    public void setObjects(long objects) {
        this.objects = objects;
    }

    public double getDataSize() {
        return dataSize;
    }

    public void setDataSize(double dataSize) {
        this.dataSize = dataSize;
    }

    public double getStorageSize() {
        return storageSize;
    }

    public void setStorageSize(double storageSize) {
        this.storageSize = storageSize;
    }

    public double getIndexSize() {
        return indexSize;
    }

    public void setIndexSize(double indexSize) {
        this.indexSize = indexSize;
    }

    public long getIndexes() {
        return indexes;
    }

    public void setIndexes(long indexes) {
        this.indexes = indexes;
    }

    public long getNumExtents() {
        return numExtents;
    }

    public void setNumExtents(long numExtents) {
        this.numExtents = numExtents;
    }
}
