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

import java.util.List;

/**
 * State of the cluster.
 *
 * @see com.mongodb.connection.ClusterSettings
 */
public class ClusterSettingsDto extends AbstractDto {

	/**
	 * Gets the host name from which to lookup SRV record for the seed list.
	 *
	 * @see com.mongodb.connection.ClusterSettings#getSrvHost()
	 */
	private String srvHost;

	/**
	 * Gets the host name from which to lookup SRV record for the seed list.
	 *
	 * @see com.mongodb.connection.ClusterSettings#getMode()
	 */
	private String mode;

	/**
	 * Gets the seed list of hosts for the cluster.
	 *
	 * @see com.mongodb.connection.ClusterSettings#getHosts()
	 */
	private List<String> hosts;

	/**
	 * Gets the required cluster type
	 *
	 * @see com.mongodb.connection.ClusterSettings#getRequiredClusterType()
	 */
	private String requiredClusterType;

	/**
	 * Gets the required replica set name.
	 *
	 * @see com.mongodb.connection.ClusterSettings#getRequiredReplicaSetName()
	 */
	private String requiredReplicaSetName;

	/**
	 * This is the maximum number of threads that may be waiting for a connection to become available from the pool. All further threads
	 * will get an exception immediately.
	 *
	 * @see com.mongodb.connection.ClusterSettings#getMaxWaitQueueSize()
	 */
	private int maxWaitQueueSize;

	/**
	 * Gets the local threshold.  When choosing among multiple MongoDB servers to send a request, the MongoClient will only
	 * send that request to a server whose ping time is less than or equal to the server with the fastest ping time plus the local
	 * threshold.
	 *
	 * @see com.mongodb.connection.ClusterSettings#getLocalThreshold
	 */
	private long localThresholdMs;

	/**
	 * Get {@link #srvHost}
	 *
	 * @return {@link #srvHost}
	 */
	public String getSrvHost() {
		return srvHost;
	}

	/**
	 * Set {@link #srvHost}
	 *
	 * @param srvHost New {@link #srvHost}
	 */
	public void setSrvHost(String srvHost) {
		this.srvHost = srvHost;
	}

	/**
	 * Get {@link #hosts}
	 *
	 * @return {@link #hosts}
	 */
	public List<String> getHosts() {
		return hosts;
	}

	/**
	 * Set  {@link #hosts}
	 *
	 * @param hosts New  {@link #hosts}
	 */
	public void setHosts(List<String> hosts) {
		this.hosts = hosts;
	}

	/**
	 * Get {@link #requiredClusterType}
	 *
	 * @return {@link #requiredClusterType}
	 */
	public String getRequiredClusterType() {
		return requiredClusterType;
	}

	/**
	 * Set  {@link #requiredClusterType}
	 *
	 * @param requiredClusterType New {@link #requiredClusterType}
	 */
	public void setRequiredClusterType(String requiredClusterType) {
		this.requiredClusterType = requiredClusterType;
	}

	/**
	 * Get {@link #requiredReplicaSetName}
	 *
	 * @return {@link #requiredReplicaSetName}
	 */
	public String getRequiredReplicaSetName() {
		return requiredReplicaSetName;
	}

	/**
	 * Set  {@link #requiredReplicaSetName}
	 *
	 * @param requiredReplicaSetName New {@link #requiredReplicaSetName}
	 */
	public void setRequiredReplicaSetName(String requiredReplicaSetName) {
		this.requiredReplicaSetName = requiredReplicaSetName;
	}

	/**
	 * Get {@link #mode}
	 *
	 * @return {@link #mode}
	 */
	public String getMode() {
		return mode;
	}

	/**
	 * Set {@link #mode}
	 *
	 * @param mode New {@link #mode}
	 */
	public void setMode(String mode) {
		this.mode = mode;
	}

	/**
	 * Get {@link #maxWaitQueueSize}
	 *
	 * @return {@link #maxWaitQueueSize}
	 */
	public int getMaxWaitQueueSize() {
		return maxWaitQueueSize;
	}

	/**
	 * Set {@link #maxWaitQueueSize}
	 *
	 * @param maxWaitQueueSize New {@link #maxWaitQueueSize}
	 */
	public void setMaxWaitQueueSize(int maxWaitQueueSize) {
		this.maxWaitQueueSize = maxWaitQueueSize;
	}

	/**
	 * Get {@link #localThresholdMs}
	 *
	 * @return {@link #localThresholdMs}
	 */
	public long getLocalThresholdMs() {
		return localThresholdMs;
	}

	/**
	 * Set {@link #localThresholdMs}
	 *
	 * @param localThresholdMs New {@link #localThresholdMs}
	 */
	public void setLocalThresholdMs(long localThresholdMs) {
		this.localThresholdMs = localThresholdMs;
	}
}
