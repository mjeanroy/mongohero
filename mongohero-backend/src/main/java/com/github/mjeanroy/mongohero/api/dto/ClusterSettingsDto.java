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
	 * This is the maximum number of threads that may be waiting for a connection to become available from the pool. All further threads
	 * will get an exception immediately.
	 *
	 * @see com.mongodb.connection.ClusterSettings#getMaxWaitQueueSize()
	 */
	private int maxWaitQueueSize;

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
}
