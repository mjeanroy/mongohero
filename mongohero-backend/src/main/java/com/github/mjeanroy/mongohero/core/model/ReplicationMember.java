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

import java.util.Date;

/**
 * A member of a replica, displayed in the {@code replSetGetStatus} response, documented here: https://docs.mongodb.com/manual/reference/command/replSetGetStatus/#output
 *
 * <a href="https://docs.mongodb.com/manual/reference/command/replSetGetStatus/">https://docs.mongodb.com/manual/reference/command/replSetGetStatus/</a>
 * <a href="https://docs.mongodb.com/manual/reference/command/replSetGetStatus/#output">https://docs.mongodb.com/manual/reference/command/replSetGetStatus/#output</a>
 * <a href="https://docs.mongodb.com/manual/reference/command/replSetGetStatus/#replSetGetStatus.members">https://docs.mongodb.com/manual/reference/command/replSetGetStatus/#replSetGetStatus.members</a>
 */
public class ReplicationMember {

	/**
	 * The identifier for the member.
	 */
	private long _id;

	/**
	 * The name of the member.
	 */
	private String name;

	/**
	 * The resolved IP address of the member.
	 * If the mongod is unable to resolve the replSetGetStatus.members[n].name to an IP address,
	 * the return value is a BSON null.
	 *
	 * Otherwise, the returned value is a string representation of the resolved IP address.
	 *
	 * <strong>New in version 4.2.</strong>
	 */
	private String ip;

	/**
	 * A number that indicates if the member is up (i.e. 1) or down (i.e. 0).
	 *
	 * The health value is only present for the other members of the replica set (i.e. not
	 * the member on which the rs.status() is run).
	 */
	private double health;

	/**
	 * An integer between 0 and 10 that represents the replica state of the member.
	 */
	private int state;

	/**
	 * A string that describes state.
	 */
	private String stateStr;

	/**
	 * The uptime field holds a value that reflects the number of seconds that this member has been online.
	 *
	 * This value does not appear for the member that returns the rs.status() data.
	 */
	private long uptime;

	/**
	 * For the current primary, an ISODate formatted date string that reflects the election
	 * date.
	 *
	 * See Replica Set High Availability for more information about elections.
	 */
	private Date electionDate;

	ReplicationMember() {
	}

	/**
	 * Get {@link #_id}
	 *
	 * @return {@link #_id}
	 */
	public long get_id() {
		return _id;
	}

	/**
	 * Get {@link #name}
	 *
	 * @return {@link #name}
	 */
	public String getName() {
		return name;
	}

	/**
	 * Get {@link #ip}
	 *
	 * @return {@link #ip}
	 */
	public String getIp() {
		return ip;
	}

	/**
	 * Get {@link #health}
	 *
	 * @return {@link #health}
	 */
	public ReplicationHealth getHealth() {
		return ReplicationHealth.getByValue(health);
	}

	/**
	 * Get {@link #state}
	 *
	 * @return {@link #state}
	 */
	public ReplicationState getState() {
		return ReplicationState.getByValue(state);
	}

	/**
	 * Get {@link #stateStr}
	 *
	 * @return {@link #stateStr}
	 */
	public String getStateStr() {
		return stateStr;
	}

	/**
	 * Get {@link #uptime}
	 *
	 * @return {@link #uptime}
	 */
	public long getUptime() {
		return uptime;
	}

	/**
	 * Get {@link #electionDate}
	 *
	 * @return {@link #electionDate}
	 */
	public Date getElectionDate() {
		return electionDate;
	}
}
