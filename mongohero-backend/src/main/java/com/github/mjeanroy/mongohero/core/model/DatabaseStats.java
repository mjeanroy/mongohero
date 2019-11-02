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

public class DatabaseStats {

    /**
     * Contains a count of the number of collections in that database.
     *
     * @see <a href="https://docs.mongodb.com/manual/reference/command/dbStats/#dbStats.collections">https://docs.mongodb.com/manual/reference/command/dbStats/#dbStats.collections</a>
     */
    private long collections;

    /**
     * Contains a count of the number of objects (i.e. documents) in the database across all collections.
     *
     * @see <a href="https://docs.mongodb.com/manual/reference/command/dbStats/#dbStats.objects">https://docs.mongodb.com/manual/reference/command/dbStats/#dbStats.objects</a>
     */
    private long objects;

    /**
     * The total size of the uncompressed data held in this database. The dataSize decreases when you remove documents.
     *
     * For databases using the WiredTiger storage engine, dataSize may be larger than storageSize if compression is enabled. The dataSize decreases when documents shrink.
     *
     * @see <a href="https://docs.mongodb.com/manual/reference/command/dbStats/#dbStats.dataSize">https://docs.mongodb.com/manual/reference/command/dbStats/#dbStats.dataSize</a>
     */
    private double dataSize;

    /**
     * The average size of each document in bytes.
     * This is the dataSize divided by the number of documents.
     * The scale argument does not affect the avgObjSize value.
     *
     * @see <a href="https://docs.mongodb.com/manual/reference/command/dbStats/#dbStats.storageSize">https://docs.mongodb.com/manual/reference/command/dbStats/#dbStats.storageSize</a>
     */
    private double storageSize;

    /**
     * The total size of all indexes created on this database.
     *
     * @see <a href="https://docs.mongodb.com/manual/reference/command/dbStats/#dbStats.indexSize">https://docs.mongodb.com/manual/reference/command/dbStats/#dbStats.indexSize</a>
     */
    private double indexSize;

    /**
     * Contains a count of the total number of indexes across all collections in the database.
     *
     * @see <a href="https://docs.mongodb.com/manual/reference/command/dbStats/#dbStats.indexes">https://docs.mongodb.com/manual/reference/command/dbStats/#dbStats.indexes</a>
     */
    private long indexes;

    /**
     * Contains a count of the number of extents in the database across all collections.
     *
     * @see <a href="https://docs.mongodb.com/manual/reference/command/dbStats/#dbStats.numExtents">https://docs.mongodb.com/manual/reference/command/dbStats/#dbStats.numExtents</a>
     */
    private long numExtents;

    DatabaseStats() {
    }

    /**
     * Get {@link #collections}
     *
     * @return {@link #collections}
     */
    public long getCollections() {
        return collections;
    }

    /**
     * Get {@link #objects}
     *
     * @return {@link #objects}
     */
    public long getObjects() {
        return objects;
    }

    /**
     * Get {@link #dataSize}
     *
     * @return {@link #dataSize}
     */
    public double getDataSize() {
        return dataSize;
    }

    /**
     * Get {@link #storageSize}
     *
     * @return {@link #storageSize}
     */
    public double getStorageSize() {
        return storageSize;
    }

    /**
     * Get {@link #indexSize}
     *
     * @return {@link #indexSize}
     */
    public double getIndexSize() {
        return indexSize;
    }

    /**
     * Get {@link #indexes}
     *
     * @return {@link #indexes}
     */
    public long getIndexes() {
        return indexes;
    }

    /**
     * Get {@link #numExtents}
     *
     * @return {@link #numExtents}
     */
    public long getNumExtents() {
        return numExtents;
    }
}
