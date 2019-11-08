package com.github.mjeanroy.mongohero.core.model;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum ReplicationState {

	STARTUP(0),
	PRIMARY(1),
	SECONDARY(2),
	RECOVERING(3),
	STARTUP2(5),
	UNKNOWN(6),
	ARBITER(7),
	DOWN(8),
	ROLLBACK(9),
	REMOVED(10);

	private final int value;

	ReplicationState(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	private static final Map<Integer, ReplicationState> map;

	static {
		map = Arrays.stream(ReplicationState.values()).collect(Collectors.toMap(
				ReplicationState::getValue,
				Function.identity()
		));
	}

	public static ReplicationState getByValue(int value) {
		return map.get(value);
	}
}
