package com.github.mjeanroy.mongohero.api.dto;

import java.util.Map;

public class ProfileQueryDto extends AbstractDto {

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
    private Map<String, Object> command;
    private Map<String, Object> query;
    private String user;

    public String getOp() {
        return op;
    }

    public void setOp(String op) {
        this.op = op;
    }

    public String getNs() {
        return ns;
    }

    public void setNs(String ns) {
        this.ns = ns;
    }

    public int getKeysExamined() {
        return keysExamined;
    }

    public void setKeysExamined(int keysExamined) {
        this.keysExamined = keysExamined;
    }

    public int getDocsExamined() {
        return docsExamined;
    }

    public void setDocsExamined(int docsExamined) {
        this.docsExamined = docsExamined;
    }

    public boolean isHasSortStage() {
        return hasSortStage;
    }

    public void setHasSortStage(boolean hasSortStage) {
        this.hasSortStage = hasSortStage;
    }

    public int getKeyUpdates() {
        return keyUpdates;
    }

    public void setKeyUpdates(int keyUpdates) {
        this.keyUpdates = keyUpdates;
    }

    public int getWriteConflicts() {
        return writeConflicts;
    }

    public void setWriteConflicts(int writeConflicts) {
        this.writeConflicts = writeConflicts;
    }

    public int getNumYield() {
        return numYield;
    }

    public void setNumYield(int numYield) {
        this.numYield = numYield;
    }

    public int getMillis() {
        return millis;
    }

    public void setMillis(int millis) {
        this.millis = millis;
    }

    public int getNreturned() {
        return nreturned;
    }

    public void setNreturned(int nreturned) {
        this.nreturned = nreturned;
    }

    public Map<String, Object> getQuery() {
        return query;
    }

    public void setQuery(Map<String, Object> query) {
        this.query = query;
    }

    public Map<String, Object> getCommand() {
        return command;
    }

    public void setCommand(Map<String, Object> command) {
        this.command = command;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
