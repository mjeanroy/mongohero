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

package com.github.mjeanroy.mongohero.core.mongo;

import com.mongodb.MongoNamespace;

/**
 * Static PreCondition utilities for MongoDB.
 */
final class MongoPreConditions {

	// Ensure non instantiation.
	private MongoPreConditions() {
	}

	/**
	 * Check validity of given database name, and:
	 * <ul>
	 *   <li>Throw {@link IllegalMongoDatabaseNameException} if it is not valid</li>
	 *   <li>Returns database name otherwise.</li>
	 * </ul>
	 *
	 * @param databaseName The database name.
	 */
	static String checkDatabaseNameValidity(String databaseName) {
		try {
			MongoNamespace.checkDatabaseNameValidity(databaseName);
			return databaseName;
		}
		catch (IllegalArgumentException ex) {
			throw new IllegalMongoDatabaseNameException(ex, databaseName);
		}
	}

	/**
	 * Check validity of given collection name, and:
	 * <ul>
	 *   <li>Throw {@link IllegalMongoCollectionNameException} if it is not valid</li>
	 *   <li>Returns collection name otherwise.</li>
	 * </ul>
	 *
	 * @param collectionName The collection name.
	 */
	static String checkCollectionNameValidity(String collectionName) {
		try {
			MongoNamespace.checkCollectionNameValidity(collectionName);
			return collectionName;
		}
		catch (IllegalArgumentException ex) {
			throw new IllegalMongoCollectionNameException(ex, collectionName);
		}
	}
}
