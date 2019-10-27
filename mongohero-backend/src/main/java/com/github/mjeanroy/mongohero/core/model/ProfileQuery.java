package com.github.mjeanroy.mongohero.core.model;

import java.util.Map;

public class ProfileQuery {
    private String op;
    private String ns;
    private int keysExamined;
    private int docsExamined;
    private boolean hasSortStage;
    private int keyUpdates;
    private int writeConflicts;
    private int numYield;
    private int millis;
    private int nreturned;
    private Map<String, Object> query;
    private String user;

    ProfileQuery() {
    }

    public String getOp() {
        return op;
    }

    public String getNs() {
        return ns;
    }

    public int getKeysExamined() {
        return keysExamined;
    }

    public int getDocsExamined() {
        return docsExamined;
    }

    public boolean isHasSortStage() {
        return hasSortStage;
    }

    public int getKeyUpdates() {
        return keyUpdates;
    }

    public int getWriteConflicts() {
        return writeConflicts;
    }

    public int getNumYield() {
        return numYield;
    }

    public int getMillis() {
        return millis;
    }

    public int getNreturned() {
        return nreturned;
    }

    public Map<String, Object> getQuery() {
        return query;
    }

    public String getUser() {
        return user;
    }
}
