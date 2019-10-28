package com.github.mjeanroy.mongohero.api.dto;

public class ProfilingStatusDto {

    private int level;
    private int slowMs;
    private int sampleRate;

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getSlowMs() {
        return slowMs;
    }

    public void setSlowMs(int slowMs) {
        this.slowMs = slowMs;
    }

    public int getSampleRate() {
        return sampleRate;
    }

    public void setSampleRate(int sampleRate) {
        this.sampleRate = sampleRate;
    }
}
