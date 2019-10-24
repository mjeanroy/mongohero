package com.github.mjeanroy.mongohere.core.model;

public class Database {

    private String name;
    private double sizeOnDisk;
    private boolean empty;

    Database() {
    }

    public String getName() {
        return name;
    }

    public double getSizeOnDisk() {
        return sizeOnDisk;
    }

    public boolean isEmpty() {
        return empty;
    }
}
