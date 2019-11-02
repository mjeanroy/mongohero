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

package com.github.mjeanroy.mongohero.core.services;

import com.github.mjeanroy.mongohero.core.model.ProfileQuery;
import com.github.mjeanroy.mongohero.core.model.ProfilingStatus;
import com.github.mjeanroy.mongohero.core.query.Page;
import com.github.mjeanroy.mongohero.core.query.PageResult;
import com.github.mjeanroy.mongohero.core.query.Sort;
import com.github.mjeanroy.mongohero.core.repository.ProfilingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProfilingService {

    private final ProfilingRepository profilingRepository;

    @Autowired
    ProfilingService(ProfilingRepository profilingRepository) {
        this.profilingRepository = profilingRepository;
    }

    /**
     * Get mongo profiler level.
     *
     * @return The profiler level.
     * @see <a href="https://docs.mongodb.com/manual/reference/command/profile/">https://docs.mongodb.com/manual/reference/command/profile/</a>
     */
    public ProfilingStatus getProfilingStatus() {
        return profilingRepository.getStatus();
    }

    /**
     * Update mongo profiler level.
     *
     * @param level New level (must be 0, 1 or 2).
     * @param slowMs The slow operation time threshold.
     * @return THe new profiler status.
     */
    public ProfilingStatus updateProfilingStatus(int level, int slowMs) {
        profilingRepository.setStatus(level, slowMs);
        return getProfilingStatus();
    }

    /**
     * Remove all currently stored queries in {@code system.profile} collection.
     *
     * Note that deleting queries in this collection need to disabled the profiling status, so it might
     * be disabled temporarily during operation.
     *
     * @param database The database name.
     */
    public void resetSlowQueries(String database) {
        ProfilingStatus currentProfilingStatus = getProfilingStatus();
        int currentProfilingLevel = currentProfilingStatus.getWas();
        int currentSlowMs = currentProfilingStatus.getSlowms();

        // Reset it temporarily
        if (currentProfilingLevel != 0) {
            updateProfilingStatus(0, currentSlowMs);
        }

        try {
            profilingRepository.removeSlowQueries(database);
        } finally {
            if (currentProfilingLevel != 0) {
                updateProfilingStatus(currentProfilingLevel, currentSlowMs);
            }
        }
    }

    public PageResult<ProfileQuery> findSlowQueries(String database, Page page, Sort sort) {
        return profilingRepository.findSlowQueries(database, page, sort);
    }
}
