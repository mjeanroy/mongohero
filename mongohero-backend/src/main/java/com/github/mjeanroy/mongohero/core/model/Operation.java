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

public class Operation {

	/**
	 * The identifier for the operation.
	 * You can pass this value to db.killOp() in the mongo shell to terminate the operation.
	 */
	private long opid;

	/**
	 * A description of the client. This string includes the connectionId.
	 */
	private String desc;

	/**
	 * The type of operation. Values are either:
	 *
	 * <ul>
	 *     <li>op</li>
	 *     <li>idleSession</li>
	 *     <li>idleCursor</li>
	 * </ul>
	 */
	private String type;

	/**
	 * The name of the host against which the operation is run.
	 */
	private String host;

	/**
	 * An identifier for the connection where the operation originated.
	 */
	private long connectionId;

	/**
	 * A string with information about where the operation originated.
	 *
	 * For multi-document transactions, client stores information about the most recent
	 * client to run an operation inside the transaction.
	 */
	private String client;

	/**
	 * A string with information about the type of client which made the request.
	 */
	private String appName;

	/**
	 * A boolean value specifying whether the operation has started.
	 * Value is true if the operation has started or false if the operation is idle, such as an
	 * idle connection or an internal thread that is currently idle.
	 * An operation can be active even if the operation has yielded to another operation.
	 */
	private boolean active;

	/**
	 * The start time of the operation.
	 */
	private String currentOpTime;

	/**
	 * The duration of the operation in seconds. MongoDB calculates this value by subtracting the
	 * current time from the start time of the operation.
	 *
	 * Only appears if the operation is running; i.e. if active is true.
	 */
	private long secs_running;

	/**
	 * A string that identifies the specific operation type. Only present if currentOp.type is op.
	 *
	 * The possible values are:
	 *
	 * <ul>
	 *     <li>"none"</li>
	 *     <li>"update"</li>
	 *     <li>"insert"</li>
	 *     <li>"query"</li>
	 *     <li>"command"</li>
	 *     <li>"getmore"</li>
	 *     <li>"remove"</li>
	 *     <li>"killcursors"</li>
	 * </ul>
	 *
	 * "query" operations include read operations.
	 * "command" operations include most commands such as the createIndexes and findandmodify.
	 */
	private String op;

	/**
	 * The namespace the operation targets.
	 * A namespace consists of the database name and the collection name concatenated with a dot (.); that
	 * is, "<database>.<collection>".
	 */
	private String ns;

	/**
	 * Returns a boolean value. waitingForLock is true if the operation is waiting for
	 * a lock and false if the operation has the required lock.
	 */
	private boolean waitingForLock;

	/**
	 * The msg provides a message that describes the status and progress of the operation.
	 * In the case of indexing or mapReduce operations, the field reports the completion percentage.
	 */
	private String msg;

	/**
	 * A document containing the full command object associated with this operation.
	 */
	private Map<String, Object> command;

	Operation() {
	}

	/**
	 * Get {@link #opid}
	 *
	 * @return {@link #opid}
	 */
	public long getOpid() {
		return opid;
	}

	/**
	 * Get {@link #desc}
	 *
	 * @return {@link #desc}
	 */
	public String getDesc() {
		return desc;
	}

	/**
	 * Get {@link #type}
	 *
	 * @return {@link #type}
	 */
	public String getType() {
		return type;
	}

	/**
	 * Get {@link #host}
	 *
	 * @return {@link #host}
	 */
	public String getHost() {
		return host;
	}

	/**
	 * Get {@link #connectionId}
	 *
	 * @return {@link #connectionId}
	 */
	public long getConnectionId() {
		return connectionId;
	}

	/**
	 * Get {@link #client}
	 *
	 * @return {@link #client}
	 */
	public String getClient() {
		return client;
	}

	/**
	 * Get {@link #appName}
	 *
	 * @return {@link #appName}
	 */
	public String getAppName() {
		return appName;
	}

	/**
	 * Get {@link #active}
	 *
	 * @return {@link #active}
	 */
	public boolean isActive() {
		return active;
	}

	/**
	 * Get {@link #currentOpTime}
	 *
	 * @return {@link #currentOpTime}
	 */
	public String getCurrentOpTime() {
		return currentOpTime;
	}

	/**
	 * Get {@link #secs_running}
	 *
	 * @return {@link #secs_running}
	 */
	public long getSecs_running() {
		return secs_running;
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
	 * Get {@link #waitingForLock}
	 *
	 * @return {@link #waitingForLock}
	 */
	public boolean isWaitingForLock() {
		return waitingForLock;
	}

	/**
	 * Get {@link #msg}
	 *
	 * @return {@link #msg}
	 */
	public String getMsg() {
		return msg;
	}

	/**
	 * Get {@link #command}
	 *
	 * @return {@link #command}
	 */
	public Map<String, Object> getCommand() {
		return command;
	}
}
