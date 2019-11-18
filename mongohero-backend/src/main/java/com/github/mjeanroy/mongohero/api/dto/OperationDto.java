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

import java.util.Map;

public class OperationDto extends AbstractDto {

	/**
	 * Operation Identifier.
	 *
	 * @see com.github.mjeanroy.mongohero.core.model.Operation#getOpid()
	 */
	private long opId;

	/**
	 * A description of the client. This string includes the connectionId.
	 *
	 * @see com.github.mjeanroy.mongohero.core.model.Operation#getDesc()
	 */
	private String desc;

	/**
	 * The type of operation.
	 *
	 * @see com.github.mjeanroy.mongohero.core.model.Operation#getType()
	 */
	private String type;

	/**
	 * The name of the host against which the operation is run.
	 *
	 * @see com.github.mjeanroy.mongohero.core.model.Operation#getHost()
	 */
	private String host;

	/**
	 * A string with information about where the operation originated.
	 *
	 * @see com.github.mjeanroy.mongohero.core.model.Operation#getClient()
	 */
	private String client;

	/**
	 * An identifier for the connection where the operation originated.
	 *
	 * @see com.github.mjeanroy.mongohero.core.model.Operation#getConnectionId()
	 */
	private long connectionId;

	/**
	 * A boolean value specifying whether the operation has started.
	 *
	 * @see com.github.mjeanroy.mongohero.core.model.Operation#isActive()
	 */
	private boolean active;

	/**
	 * The start time of the operation.
	 *
	 * @see com.github.mjeanroy.mongohero.core.model.Operation#getCurrentOpTime()
	 */
	private String currentOpTime;

	/**
	 * The duration of the operation in seconds.
	 *
	 * @see com.github.mjeanroy.mongohero.core.model.Operation#getSecs_running()
	 */
	private long secsRunning;

	/**
	 * A string that identifies the specific operation type.
	 *
	 * @see com.github.mjeanroy.mongohero.core.model.Operation#getOp()
	 */
	private String op;

	/**
	 * The namespace the operation targets.
	 *
	 * @see com.github.mjeanroy.mongohero.core.model.Operation#getNs()
	 */
	private String ns;

	/**
	 * Returns {@code true} if the operation is waiting for
	 * a lock and {@code false} if the operation has the required lock.
	 *
	 * @see com.github.mjeanroy.mongohero.core.model.Operation#isWaitingForLock()
	 */
	private boolean waitingForLock;

	/**
	 * The msg provides a message that describes the status and progress of the operation.
	 *
	 * @see com.github.mjeanroy.mongohero.core.model.Operation#getMsg()
	 */
	private String msg;

	/**
	 * A document containing the full command object associated with this operation.
	 *
	 * @see com.github.mjeanroy.mongohero.core.model.Operation#getCommand()
	 */
	private Map<String, Object> command;

	/**
	 * Get {@link #opId}
	 *
	 * @return {@link #opId}
	 */
	public long getOpId() {
		return opId;
	}

	/**
	 * Set {@link #opId}
	 *
	 * @param opId New {@link #opId}
	 */
	public void setOpId(long opId) {
		this.opId = opId;
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
	 * Set {@link #desc}
	 *
	 * @param desc New {@link #desc}
	 */
	public void setDesc(String desc) {
		this.desc = desc;
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
	 * Set {@link #type}
	 *
	 * @param type New {@link #type}
	 */
	public void setType(String type) {
		this.type = type;
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
	 * Set {@link #host}
	 *
	 * @param host New {@link #host}
	 */
	public void setHost(String host) {
		this.host = host;
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
	 * Set {@link #client}
	 *
	 * @param client New {@link #client}
	 */
	public void setClient(String client) {
		this.client = client;
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
	 * Set {@link #connectionId}
	 *
	 * @param connectionId New {@link #connectionId}
	 */
	public void setConnectionId(long connectionId) {
		this.connectionId = connectionId;
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
	 * Set {@link #active}
	 *
	 * @param active New {@link #active}
	 */
	public void setActive(boolean active) {
		this.active = active;
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
	 * Set {@link #currentOpTime}
	 *
	 * @param currentOpTime New {@link #currentOpTime}
	 */
	public void setCurrentOpTime(String currentOpTime) {
		this.currentOpTime = currentOpTime;
	}

	/**
	 * Get {@link #secsRunning}
	 *
	 * @return {@link #secsRunning}
	 */
	public long getSecsRunning() {
		return secsRunning;
	}

	/**
	 * Set {@link #secsRunning}
	 *
	 * @param secsRunning New {@link #secsRunning}
	 */
	public void setSecsRunning(long secsRunning) {
		this.secsRunning = secsRunning;
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
	 * Set {@link #op}
	 *
	 * @param op New {@link #op}
	 */
	public void setOp(String op) {
		this.op = op;
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
	 * Set {@link #ns}
	 *
	 * @param ns New {@link #ns}
	 */
	public void setNs(String ns) {
		this.ns = ns;
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
	 * Set {@link #waitingForLock}
	 *
	 * @param waitingForLock New {@link #waitingForLock}
	 */
	public void setWaitingForLock(boolean waitingForLock) {
		this.waitingForLock = waitingForLock;
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
	 * Set {@link #msg}
	 *
	 * @param msg New {@link #msg}
	 */
	public void setMsg(String msg) {
		this.msg = msg;
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
	 * Set {@link #command}
	 *
	 * @param command New {@link #command}
	 */
	public void setCommand(Map<String, Object> command) {
		this.command = command;
	}
}
