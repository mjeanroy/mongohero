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

import com.github.mjeanroy.mongohero.api.dto.OperationDto;
import com.github.mjeanroy.mongohero.core.model.Operation;
import com.github.mjeanroy.mongohero.core.tests.builders.OperationBuilder;
import org.junit.jupiter.api.BeforeEach;

import static org.assertj.core.api.Assertions.assertThat;

class OperationDtoMapperTest extends AbstractDtoMapperTest<OperationDto, Operation> {

	private OperationDtoMapper mapper;

	@BeforeEach
	void setUp() {
		mapper = new OperationDtoMapper();
	}

	@Override
	OperationDtoMapper getMapper() {
		return mapper;
	}

	@Override
	Operation givenInput() {
		return new OperationBuilder().withAppName("mongohero").build();
	}

	@Override
	void verifyMapping(Operation input, OperationDto output) {
		assertThat(output.getAppName()).isEqualTo(input.getAppName());
	}
}
