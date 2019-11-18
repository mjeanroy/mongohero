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
