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

package com.github.mjeanroy.mongohero.api.mappers;

import com.github.mjeanroy.mongohero.commons.Streams;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class AbstractDtoMapper<T, U> {

	/**
	 * Map given stream of inputs to a stream of outputs.
	 *
	 * This method is null safe and will returns {@code null} if {@code inputs} is {@code null}.
	 *
	 * @param inputs Stream of inputs.
	 * @return Stream of outputs.
	 */
	public final Stream<T> map(Stream<U> inputs) {
		if (inputs == null) {
			return null;
		}

		return inputs.map(this::map);
	}

	/**
	 * Map given stream of inputs to an in memory list of outputs.
	 *
	 * This method is null safe and will returns {@code null} if {@code inputs} is {@code null}.
	 *
	 * @param inputs Stream of inputs.
	 * @return List of outputs.
	 */
	public final List<T> mapToList(Stream<U> inputs) {
		if (inputs == null) {
			return null;
		}

		return inputs.map(this::map).collect(Collectors.toList());
	}

	/**
	 * Map given inputs to an in memory list of outputs.
	 *
	 * This method is null safe and will returns {@code null} if {@code inputs} is {@code null}.
	 *
	 * @param inputs Inputs.
	 * @return List of outputs.
	 */
	public final List<T> mapToList(Iterable<U> inputs) {
		if (inputs == null) {
			return null;
		}

		return Streams.toStream(inputs).map(this::map).collect(Collectors.toList());
	}

	/**
	 * Map input to given output.
	 *
	 * This method is null-safe and will returns {@code null} if {@code input} is {@code null}.
	 *
	 * @param input The input.
	 * @return The output.
	 */
	public final T map(U input) {
		if (input == null) {
			return null;
		}

		return doMap(input);
	}

	/**
	 * Map input to given output.
	 *
	 * Unless called explicitly, the parameter {@code input} will never be {@code null}.
	 *
	 * @param input Given input.
	 * @return The output value.
	 */
	abstract T doMap(U input);
}
