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

package com.github.mjeanroy.mongohere.core.model;

public class Server {

    /**
     * System’s hostname.
     * In Unix/Linux systems, this should be the same as the output of the hostname command.
     */
    private String host;

    /**
     * The MongoDB version of the current MongoDB process.
     */
    private String version;

    /**
     * The number of seconds that the current MongoDB process has been active.
     */
    private double uptime;

    /**
     * Reports on the status of the connections.
     * Use these values to assess the current load and capacity requirements of the server.
     */
    private ServerConnections connections;

    /**
     * Data about the current storage engine.
     */
    private ServerStorageEngine storageEngine;

    Server() {
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
     * Get {@link #version}
     *
     * @return {@link #version}
     */
    public String getVersion() {
        return version;
    }

    /**
     * Get {@link #uptime}
     *
     * @return {@link #uptime}
     */
    public double getUptime() {
        return uptime;
    }

    /**
     * Get {@link #connections}
     *
     * @return {@link #connections}
     */
    public ServerConnections getConnections() {
        return connections;
    }

    /**
     * Get {@link #storageEngine}
     *
     * @return {@link #storageEngine}
     */
    public ServerStorageEngine getStorageEngine() {
        return storageEngine;
    }
}
