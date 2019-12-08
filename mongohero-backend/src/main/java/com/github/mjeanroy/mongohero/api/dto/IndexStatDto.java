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

package com.github.mjeanroy.mongohero.api.dto;

import java.util.Map;

public class IndexStatDto extends AbstractDto {

	/**
	 * Index name.
	 */
	private String name;

	/**
	 * Index key specification.
	 */
	private Map<String, Number> key;

	/**
	 * Statistics on the index use:
	 *
	 * <ul>
	 *   <li>ops is the number of operations that used the index</li>
	 *   <li>since is the time from which MongoDB gathered the statistics</li>
	 * </ul>
	 */
	private IndexAccessDto accesses;

	/**
	 * Get {@link #name}
	 *
	 * @return {@link #name}
	 */
	public String getName() {
		return name;
	}

	/**
	 * Set {@link #name}
	 *
	 * @param name New {@link #name}
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Get {@link #key}
	 *
	 * @return {@link #key}
	 */
	public Map<String, Number> getKey() {
		return key;
	}

	/**
	 * Set {@link #key}
	 *
	 * @param key New {@link #key}
	 */
	public void setKey(Map<String, Number> key) {
		this.key = key;
	}

	/**
	 * Get {@link #accesses}
	 *
	 * @return {@link #accesses}
	 */
	public IndexAccessDto getAccesses() {
		return accesses;
	}

	/**
	 * Set {@link #accesses}
	 *
	 * @param accesses New {@link #accesses}
	 */
	public void setAccesses(IndexAccessDto accesses) {
		this.accesses = accesses;
	}
}
