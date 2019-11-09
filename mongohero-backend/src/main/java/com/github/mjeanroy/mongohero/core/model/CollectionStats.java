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

import java.util.Map;

public class CollectionStats {

	/**
	 * The namespace of the current collection, which follows the format [database].[collection].
	 */
	private String ns;

	/**
	 * The total uncompressed size in memory of all records in a collection.
	 * The size does not include the size of any indexes associated with the collection, which the totalIndexSize field reports.
	 *
	 * The scale argument affects this value. Data compression does not affect this value.
	 */
	private double size;

	/**
	 * The number of objects or documents in this collection.
	 */
	private long count;

	/**
	 * The average size of an object in the collection.
	 * The scale argument does not affect this value.
	 */
	private double avgObjSize;

	/**
	 * The total amount of storage allocated to this collection for document storage.
	 * The scale argument affects this value.
	 *
	 * If collection data is compressed (which is the default for WiredTiger),
	 * the storage size reflects the compressed size and may be smaller than the value for collStats.size.
	 *
	 * storageSize does not include index size. See totalIndexSize for index sizing.
	 */
	private double storageSize;

	/**
	 * This field will be “true” if the collection is capped.
	 */
	private boolean capped;

	/**
	 * The number of indexes on the collection. All collections have at least one index on the _id field.
	 *
	 * Starting in MongoDB 4.2, nindexes includes in its count those indexes currently being built.
	 */
	private long nindexes;

	/**
	 * The total size of all indexes. The scale argument affects this value.
	 *
	 * If an index uses prefix compression (which is the default for WiredTiger),
	 * the returned size reflects the compressed size for any such indexes when calculating the total.
	 *
	 * Starting in MongoDB 4.2, totalIndexSize includes in its total the size of those indexes currently being built.
	 */
	private double totalIndexSize;

	/**
	 * This field specifies the key and size of every existing index on the collection.
	 * The scale argument affects this value.
	 *
	 * If an index uses prefix compression (which is the default for WiredTiger),
	 * the returned size reflects the compressed size.
	 *
	 * Starting in MongoDB 4.2, indexSizes includes sizes of indexes currently being built.
	 */
	private Map<String, Integer> indexSizes;

	CollectionStats() {
	}

	/**
	 * Get {@link #ns}
	 *
	 * @return {@link #ns}
	 */
	public String getNs() {
		return ns;
	}

	/**
	 * Get {@link #size}
	 *
	 * @return {@link #size}
	 */
	public double getSize() {
		return size;
	}

	/**
	 * Get {@link #count}
	 *
	 * @return {@link #count}
	 */
	public long getCount() {
		return count;
	}

	/**
	 * Get {@link #avgObjSize}
	 *
	 * @return {@link #avgObjSize}
	 */
	public double getAvgObjSize() {
		return avgObjSize;
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
	 * Get {@link #capped}
	 *
	 * @return {@link #capped}
	 */
	public boolean isCapped() {
		return capped;
	}

	/**
	 * Get {@link #nindexes}
	 *
	 * @return {@link #nindexes}
	 */
	public long getNindexes() {
		return nindexes;
	}

	/**
	 * Get {@link #totalIndexSize}
	 *
	 * @return {@link #totalIndexSize}
	 */
	public double getTotalIndexSize() {
		return totalIndexSize;
	}

	/**
	 * Get {@link #indexSizes}
	 *
	 * @return {@link #indexSizes}
	 */
	public Map<String, Integer> getIndexSizes() {
		return indexSizes;
	}
}
