package com.github.mjeanroy.mongohere.api.dto;

public class DatabaseDto {

    private String name;
    private double sizeOnDisk;
    private boolean empty;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getSizeOnDisk() {
        return sizeOnDisk;
    }

    public void setSizeOnDisk(double sizeOnDisk) {
        this.sizeOnDisk = sizeOnDisk;
    }

    public boolean isEmpty() {
        return empty;
    }

    public void setEmpty(boolean empty) {
        this.empty = empty;
    }
}
