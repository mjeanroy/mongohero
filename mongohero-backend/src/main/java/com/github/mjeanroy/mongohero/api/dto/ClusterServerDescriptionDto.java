package com.github.mjeanroy.mongohero.api.dto;

import java.util.List;

/**
 * State of a server.
 *
 * @see com.mongodb.connection.ServerDescription
 */
public class ClusterServerDescriptionDto extends AbstractDto {

	/**
	 * Gets the address of this server
	 *
	 * @see com.mongodb.connection.ServerDescription#getAddress()
	 */
	private ClusterServerAddressDto address;

	/**
	 * The server election identifier.
	 *
	 * @see com.mongodb.connection.ServerDescription#getElectionId()
	 */
	private String electionId;

	/**
	 * Gets the current state of the connection to the server.
	 *
	 * @see com.mongodb.connection.ServerDescription#getState()
	 */
	private String state;

	/**
	 * Gets the type of the server, for example whether it's a standalone or in a replica set.
	 *
	 * @see com.mongodb.connection.ServerDescription#getType()
	 */
	private String type;

	/**
	 * Gets the string representing the host name and port that this member of a replica set was configured with,
	 * e.g. {@code "somehost:27019"}. This is typically derived from the "me" field from the "isMaster" command response.
	 *
	 * @see com.mongodb.connection.ServerDescription#getCanonicalAddress()
	 */
	private String canonicalAddress;

	/**
	 * Get a Set of strings in the format of "[hostname]:[port]" that contains all members of the replica set that are neither hidden,
	 * passive, nor arbiters.
	 *
	 * @see com.mongodb.connection.ServerDescription#getHosts()
	 */
	private List<String> hosts;

	/**
	 * Gets the passive members of the replica set.
	 *
	 * @see com.mongodb.connection.ServerDescription#getPassives()
	 */
	private List<String> passives;

	/**
	 * Gets the arbiters in the replica set
	 *
	 * @see com.mongodb.connection.ServerDescription#getArbiters()
	 */
	private List<String> arbiters;

	/**
	 * Gets the address of the current primary in the replica set
	 *
	 * @see com.mongodb.connection.ServerDescription#getPrimary()
	 */
	private String primary;

	/**
	 * Get the time it took to make the round trip for requesting this information from the server in nanoseconds.
	 *
	 * @see com.mongodb.connection.ServerDescription#getRoundTripTimeNanos()
	 */
	private long roundTripTimeNanos;

	/**
	 * The maximum permitted size of a BSON object in bytes for this mongod process. Defaults to 16MB.
	 *
	 * @see com.mongodb.connection.ServerDescription#getMaxDocumentSize()
	 */
	private int maxDocumentSize;

	/**
	 * Get {@link #address}
	 *
	 * @return {@link #address}
	 */
	public ClusterServerAddressDto getAddress() {
		return address;
	}

	/**
	 * Set {@link #address}
	 *
	 * @param address New {@link #address}
	 */
	public void setAddress(ClusterServerAddressDto address) {
		this.address = address;
	}

	/**
	 * Get {@link #state}
	 *
	 * @return {@link #state}
	 */
	public String getState() {
		return state;
	}

	/**
	 * Set  {@link #state}
	 *
	 * @param state New {@link #state}
	 */
	public void setState(String state) {
		this.state = state;
	}

	/**
	 * Get {@link #electionId}
	 *
	 * @return {@link #electionId}
	 */
	public String getElectionId() {
		return electionId;
	}

	/**
	 * Set {@link #electionId}
	 *
	 * @param electionId New {@link #electionId}
	 */
	public void setElectionId(String electionId) {
		this.electionId = electionId;
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
	 * Get {@link #roundTripTimeNanos}
	 *
	 * @return {@link #roundTripTimeNanos}
	 */
	public long getRoundTripTimeNanos() {
		return roundTripTimeNanos;
	}

	/**
	 * Set {@link #roundTripTimeNanos}
	 *
	 * @param roundTripTimeNanos New {@link #roundTripTimeNanos}
	 */
	public void setRoundTripTimeNanos(long roundTripTimeNanos) {
		this.roundTripTimeNanos = roundTripTimeNanos;
	}

	/**
	 * Get {@link #maxDocumentSize}
	 *
	 * @return {@link #maxDocumentSize}
	 */
	public int getMaxDocumentSize() {
		return maxDocumentSize;
	}

	/**
	 * Set {@link #maxDocumentSize}
	 *
	 * @param maxDocumentSize New {@link #maxDocumentSize}
	 */
	public void setMaxDocumentSize(int maxDocumentSize) {
		this.maxDocumentSize = maxDocumentSize;
	}

	/**
	 * Get {@link #canonicalAddress}
	 *
	 * @return {@link #canonicalAddress}
	 */
	public String getCanonicalAddress() {
		return canonicalAddress;
	}

	/**
	 * Set {@link #canonicalAddress}
	 *
	 * @param canonicalAddress New {@link #canonicalAddress}
	 */
	public void setCanonicalAddress(String canonicalAddress) {
		this.canonicalAddress = canonicalAddress;
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
	 * Set {@link #hosts}
	 *
	 * @param hosts New {@link #hosts}
	 */
	public void setHosts(List<String> hosts) {
		this.hosts = hosts;
	}

	/**
	 * Get {@link #passives}
	 *
	 * @return {@link #passives}
	 */
	public List<String> getPassives() {
		return passives;
	}

	/**
	 * Set {@link #passives}
	 *
	 * @param passives New {@link #passives}
	 */
	public void setPassives(List<String> passives) {
		this.passives = passives;
	}

	/**
	 * Get {@link #arbiters}
	 *
	 * @return {@link #arbiters}
	 */
	public List<String> getArbiters() {
		return arbiters;
	}

	/**
	 * Set {@link #arbiters}
	 *
	 * @param arbiters New {@link #arbiters}
	 */
	public void setArbiters(List<String> arbiters) {
		this.arbiters = arbiters;
	}

	/**
	 * Get {@link #primary}
	 *
	 * @return {@link #primary}
	 */
	public String getPrimary() {
		return primary;
	}

	/**
	 * Set {@link #primary}
	 *
	 * @param primary New {@link #primary}
	 */
	public void setPrimary(String primary) {
		this.primary = primary;
	}
}
