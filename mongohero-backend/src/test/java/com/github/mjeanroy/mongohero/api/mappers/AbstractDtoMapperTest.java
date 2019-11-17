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

import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

abstract class AbstractDtoMapperTest<T, U> {

	@Test
	void it_should_map_null_to_null() {
		assertThat(getMapper().map((U) null)).isNull();
		assertThat(getMapper().map((Stream<U>) null)).isNull();
	}

	@Test
	void it_should_map_input_to_output() {
		U input = givenInput();
		T output = getMapper().map(input);
		verifyMapping(input, output);
	}

	@Test
	void it_should_map_inputs_to_outputs() {
		List<U> inputs = asList(givenInput(), givenInput());
		List<T> outputs = getMapper().map(inputs.stream()).collect(Collectors.toList());

		assertThat(outputs).isNotEmpty().hasSameSizeAs(inputs);

		Iterator<U> it1 = inputs.iterator();
		Iterator<T> it2 = outputs.iterator();
		while (it1.hasNext()) {
			verifyMapping(it1.next(), it2.next());
		}
	}

	/**
	 * Get mapper to test.
	 *
	 * @return The mapper.
	 */
	abstract AbstractDtoMapper<T, U> getMapper();

	abstract U givenInput();

	abstract void verifyMapping(U input, T output);
}
