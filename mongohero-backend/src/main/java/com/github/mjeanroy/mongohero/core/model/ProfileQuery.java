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

public class ProfileQuery {

	/**
	 * The type of operation. The possible values are:
	 *
	 * <ul>
	 *     <li>command</li>
	 *     <li>count</li>
	 *     <li>distinct</li>
	 *     <li>geoNear</li>
	 *     <li>getMore</li>
	 *     <li>group</li>
	 *     <li>insert</li>
	 *     <li>mapReduce</li>
	 *     <li>query</li>
	 *     <li>remove</li>
	 *     <li>update</li>
	 * </ul>
	 */
	private String op;

	/**
	 * The namespace the operation targets. Namespaces in MongoDB take the form of the database,
	 * followed by a dot (.), followed by the name of the collection.
	 */
	private String ns;

	/**
	 *  The number of index keys that MongoDB scanned in order to carry out the operation.
	 *
	 * In general, if keysExamined is much higher than nreturned, the database is scanning many index keys to find
	 * the result documents.
	 *
	 * Consider creating or adjusting indexes to improve query performance..
	 */
	private int keysExamined;

	/**
	 * The number of documents in the collection that MongoDB scanned in order to carry out the operation.
	 */
	private int docsExamined;

	/**
	 * hasSortStage is a boolean that is true when a query cannot use the ordering in the index to return
	 * the requested sorted results; i.e. MongoDB must sort the documents after it receives the documents from a
	 * cursor.
	 *
	 * The field only appears when the value is true.
	 */
	private boolean hasSortStage;

	/**
	 * The number of key updated by the operation.
	 */
	private int keyUpdates;

	/**
	 * The number of conflicts encountered during the write operation; e.g.
	 * an update operation attempts to modify the same document as another update operation.
	 */
	private int writeConflicts;

	/**
	 * The number of times the operation yielded to allow other operations to complete.
	 * Typically, operations yield when they need access to data that MongoDB has not yet fully
	 * read into memory.
	 *
	 * This allows other operations that have data in memory to complete while MongoDB reads in data for the
	 * yielding operation.
	 */
	private int numYield;

	/**
	 * The time in milliseconds from the perspective of the mongod from the beginning of the operation to the end of the operation.
	 */
	private int millis;

	/**
	 * The number of documents returned by the operation.
	 */
	private int nreturned;

	/**
	 * A document containing the full command object associated with this operation.
	 */
	private Map<String, Object> command;

	/**
	 * The query operation.
	 */
	private Map<String, Object> query;

	/**
	 * The authenticated user who ran the operation.
	 * If the operation was not run by an authenticated user, this field’s value is an empty string.
	 */
	private String user;

	ProfileQuery() {
	}

	/**
	 * Get {@link #op}
	 *
	 * @return {@link #op}
	 */
	public String getOp() {
		return op;
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
	 * Get {@link #keysExamined}
	 *
	 * @return {@link #keysExamined}
	 */
	public int getKeysExamined() {
		return keysExamined;
	}

	/**
	 * Get {@link #docsExamined}
	 *
	 * @return {@link #docsExamined}
	 */
	public int getDocsExamined() {
		return docsExamined;
	}

	/**
	 * Get {@link #hasSortStage}
	 *
	 * @return {@link #hasSortStage}
	 */
	public boolean isHasSortStage() {
		return hasSortStage;
	}

	/**
	 * Get {@link #keyUpdates}
	 *
	 * @return {@link #keyUpdates}
	 */
	public int getKeyUpdates() {
		return keyUpdates;
	}

	/**
	 * Get {@link #writeConflicts}
	 *
	 * @return {@link #writeConflicts}
	 */
	public int getWriteConflicts() {
		return writeConflicts;
	}

	/**
	 * Get {@link #numYield}
	 *
	 * @return {@link #numYield}
	 */
	public int getNumYield() {
		return numYield;
	}

	/**
	 * Get {@link #millis}
	 *
	 * @return {@link #millis}
	 */
	public int getMillis() {
		return millis;
	}

	/**
	 * Get {@link #nreturned}
	 *
	 * @return {@link #nreturned}
	 */
	public int getNreturned() {
		return nreturned;
	}

	/**
	 * Get {@link #query}
	 *
	 * @return {@link #query}
	 */
	public Map<String, Object> getQuery() {
		return query;
	}

	/**
	 * Get {@link #command}
	 *
	 * @return {@link #command}
	 */
	public Map<String, Object> getCommand() {
		return command;
	}

	/**
	 * Get {@link #user}
	 *
	 * @return {@link #user}
	 */
	public String getUser() {
		return user;
	}
}
