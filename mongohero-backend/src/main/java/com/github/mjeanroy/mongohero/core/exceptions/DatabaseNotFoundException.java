package com.github.mjeanroy.mongohero.core.exceptions;

public class DatabaseNotFoundException extends RuntimeException {

    private final String dbName;

    public DatabaseNotFoundException(String dbName) {
        super(createMessage(dbName));
        this.dbName = dbName;
    }

    public String getDbName() {
        return dbName;
    }

    private static String createMessage(String dbName) {
        return "Database '" + dbName + "' does not exist";
    }
}
