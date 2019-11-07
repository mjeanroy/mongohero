package com.github.mjeanroy.mongohero.core.model;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum ReplicationHealth {

    UP(1),
    DOWN(0);

    private final double value;

    ReplicationHealth(int value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }

    private static final Map<Double, ReplicationHealth> map;

    static {
        map = Arrays.stream(ReplicationHealth.values()).collect(Collectors.toMap(
                ReplicationHealth::getValue,
                Function.identity()
        ));
    }

    public static ReplicationHealth getByValue(double value) {
        return map.get(value);
    }
}
