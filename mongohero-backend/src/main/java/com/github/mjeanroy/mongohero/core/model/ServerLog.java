package com.github.mjeanroy.mongohero.core.model;

import java.util.List;

public class ServerLog {

    private long totalLinesWritten;
    private List<String> log;

    ServerLog() {
    }

    /**
     * Get {@link #totalLinesWritten}
     *
     * @return {@link #totalLinesWritten}
     */
    public long getTotalLinesWritten() {
        return totalLinesWritten;
    }

    /**
     * Get {@link #log}
     *
     * @return {@link #log}
     */
    public List<String> getLog() {
        return log;
    }
}
