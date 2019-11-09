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
import java.util.List;

/**
 * The {@code replSetGetStatus} response, documented here: https://docs.mongodb.com/manual/reference/command/replSetGetStatus/#output
 *
 * <a href="https://docs.mongodb.com/manual/reference/command/replSetGetStatus/">https://docs.mongodb.com/manual/reference/command/replSetGetStatus/</a>
 * <a href="https://docs.mongodb.com/manual/reference/command/replSetGetStatus/#output">https://docs.mongodb.com/manual/reference/command/replSetGetStatus/#output</a>
 */
public class ReplicationStatus {

	/**
	 * The set value is the name of the replica set, configured in the replSetName setting.
	 * This is the same value as _id in rs.conf().
	 */
	private String set;

	/**
	 * An ISODate formatted date and time that reflects the current time according to the server
	 * that processed the replSetGetStatus command.
	 *
	 * Compare this to the values of replSetGetStatus.members[n].lastHeartbeat to find the
	 * operational latency between this server and the other members of the replica set.
	 */
	private Date date;

	/**
	 * An integer between 0 and 10 that represents the replica state of the current member.
	 *
	 * <ul>
	 *     <li>0 -> STARTUP</li>
	 *     <li>1 -> PRIMARY</li>
	 *     <li>2 -> SECONDARY</li>
	 *     <li>3 -> RECOVERING</li>
	 *     <li>5 -> STARTUP2</li>
	 *     <li>6 -> UNKNOWN</li>
	 *     <li>7 -> ARBITER</li>
	 *     <li>8 -> DOWN</li>
	 *     <li>9 -> ROLLBACK</li>
	 *     <li>10 -> REMOVED</li>
	 * </ul>
	 *
	 * @see <a href="https://docs.mongodb.com/manual/reference/replica-states/">https://docs.mongodb.com/manual/reference/replica-states/</a>
	 */
	private int myState;

	/**
	 * The election count for the replica set, as known to this replica set member.
	 * The term is used by the distributed consensus algorithm to ensure correctness.
	 */
	private long term;

	/**
	 * The frequency in milliseconds of the heartbeats.
	 */
	private long heartbeatIntervalMillis;

	/**
	 * The number that corresponds to the majority votes needed to elect a new primary in an election.
	 *
	 * <strong>Available starting in 4.2.1</strong>
	 */
	private Integer majorityVoteCount;

	/**
	 * The number of data-bearing voting members (i.e. not arbiters) nee
	 *
	 * <strong>Available starting in 4.2.1</strong>
	 */
	private Integer writeMajorityCount;

	/**
	 * The members field holds an array that contains a document for every member in the replica set.
	 */
	private List<ReplicationMember> members;

	ReplicationStatus() {
	}

	/**
	 * Get {@link #set}
	 *
	 * @return {@link #set}
	 */
	public String getSet() {
		return set;
	}

	/**
	 * Get {@link #date}
	 *
	 * @return {@link #date}
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * Get {@link #myState}
	 *
	 * @return {@link #myState}
	 */
	public ReplicationState getMyState() {
		return ReplicationState.getByValue(myState);
	}

	/**
	 * Get {@link #term}
	 *
	 * @return {@link #term}
	 */
	public long getTerm() {
		return term;
	}

	/**
	 * Get {@link #heartbeatIntervalMillis}
	 *
	 * @return {@link #heartbeatIntervalMillis}
	 */
	public long getHeartbeatIntervalMillis() {
		return heartbeatIntervalMillis;
	}

	/**
	 * Get {@link #majorityVoteCount}
	 *
	 * @return {@link #majorityVoteCount}
	 */
	public Integer getMajorityVoteCount() {
		return majorityVoteCount;
	}

	/**
	 * Get {@link #writeMajorityCount}
	 *
	 * @return {@link #writeMajorityCount}
	 */
	public Integer getWriteMajorityCount() {
		return writeMajorityCount;
	}

	/**
	 * Get {@link #members}
	 *
	 * @return {@link #members}
	 */
	public List<ReplicationMember> getMembers() {
		return members;
	}
}
