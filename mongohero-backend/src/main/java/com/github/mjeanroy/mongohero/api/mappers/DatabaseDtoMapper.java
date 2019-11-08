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

import com.github.mjeanroy.mongohero.api.dto.DatabaseDto;
import com.github.mjeanroy.mongohero.core.model.Database;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DatabaseDtoMapper extends AbstractDtoMapper<DatabaseDto, Database> {

	private final DatabaseStatsDtoMapper databaseStatsDtoMapper;

	@Autowired
	DatabaseDtoMapper(DatabaseStatsDtoMapper databaseStatsDtoMapper) {
		this.databaseStatsDtoMapper = databaseStatsDtoMapper;
	}

	@Override
	DatabaseDto doMap(Database database) {
		DatabaseDto dto = new DatabaseDto();
		dto.setName(database.getName());
		dto.setSizeOnDisk(database.getSizeOnDisk());
		dto.setEmpty(database.isEmpty());
		dto.setStats(databaseStatsDtoMapper.map(database.getStats()));
		return dto;
	}
}
