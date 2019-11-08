/**
 * The MIT License (MIT)
 * <p>
 * Copyright (c) 2019 Mickael Jeanroy
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * <p>
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
import org.springframework.stereotype.Component;

@Component
public class OperationDtoMapper extends AbstractDtoMapper<OperationDto, Operation> {

	@Override
	OperationDto doMap(Operation operation) {
		OperationDto dto = new OperationDto();
		dto.setOp(operation.getOp());
		dto.setDesc(operation.getDesc());
		dto.setHost(operation.getHost());
		dto.setClient(operation.getClient());
		dto.setNs(operation.getNs());
		dto.setOpId(operation.getOpid());
		dto.setConnectionId(operation.getConnectionId());
		dto.setCurrentOpTime(operation.getCurrentOpTime());
		dto.setActive(operation.isActive());
		dto.setMsg(operation.getMsg());
		dto.setSecsRunning(operation.getSecs_running());
		dto.setType(operation.getType());
		dto.setWaitingForLock(operation.isWaitingForLock());
		dto.setCommand(operation.getCommand());
		return dto;
	}
}
