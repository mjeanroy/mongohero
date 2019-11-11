package com.github.mjeanroy.mongohero.api.dto;

/**
 * State of a server.
 *
 * @see com.mongodb.connection.ServerDescription
 */
public class ClusterServerAddressDto extends AbstractDto {

	/**
	 * Gets the hostname
	 *
	 * @see com.mongodb.ServerAddress#getHost()
	 */
	private String host;

	/**
	 * Gets the port number
	 *
	 * @see com.mongodb.ServerAddress#getPort()
	 */
	private int port;

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
	 * @param host {@link #host}
	 */
	public void setHost(String host) {
		this.host = host;
	}

	/**
	 * Get {@link #port}
	 *
	 * @return {@link #port}
	 */
	public int getPort() {
		return port;
	}

	/**
	 * Set {@link #port}
	 *
	 * @param port {@link #port}
	 */
	public void setPort(int port) {
		this.port = port;
	}
}
