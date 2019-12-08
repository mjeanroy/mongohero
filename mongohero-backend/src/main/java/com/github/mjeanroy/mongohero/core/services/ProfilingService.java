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
import com.github.mjeanroy.mongohero.core.query.ProfileQueryFilter;
import com.github.mjeanroy.mongohero.core.query.Sort;
import com.github.mjeanroy.mongohero.core.repository.ProfilingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

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
	 * @param db The database name.
	 * @return The profiler level.
	 * @see <a href="https://docs.mongodb.com/manual/reference/command/profile/">https://docs.mongodb.com/manual/reference/command/profile/</a>
	 */
	public ProfilingStatus getProfilingStatus(String db) {
		return profilingRepository.getStatus(db);
	}

	/**
	 * Update mongo profiler level for given database..
	 *
	 * @param db     The database name.
	 * @param level  New level (must be 0, 1 or 2).
	 * @param slowMs The slow operation time threshold.
	 * @return THe new profiler status.
	 */
	public ProfilingStatus updateProfilingStatus(String db, int level, int slowMs) {
		profilingRepository.setStatus(db, level, slowMs);
		return getProfilingStatus(db);
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
		ProfilingStatus currentProfilingStatus = getProfilingStatus(database);
		int currentProfilingLevel = currentProfilingStatus.getWas();
		int currentSlowMs = currentProfilingStatus.getSlowms();

		// Reset it temporarily
		if (currentProfilingLevel != 0) {
			updateProfilingStatus(database, 0, currentSlowMs);
		}

		try {
			profilingRepository.removeSlowQueries(database);
		}
		finally {
			if (currentProfilingLevel != 0) {
				updateProfilingStatus(database, currentProfilingLevel, currentSlowMs);
			}
		}
	}

	/**
	 * Find all slow queries on the cluster.
	 *
	 * Since profiling collection is not replicated across the cluster, querying for slow queries across the cluster needs to
	 * query each member of the cluster one by one.
	 *
	 * @param database The database to query.
	 * @param filter   The filters to apply.
	 * @param page     The page to query.
	 * @param sort     The sort to apply.
	 * @return The results for each member of the cluster.
	 */
	public Map<String, PageResult<ProfileQuery>> findSlowQueries(String database, ProfileQueryFilter filter, Page page, Sort sort) {
		return profilingRepository.findSlowQueries(database, filter, page, sort);
	}
}
