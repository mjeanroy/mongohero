/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2019 Mickael Jeanroy
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

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
