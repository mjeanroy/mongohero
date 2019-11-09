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

import com.github.mjeanroy.mongohero.api.dto.ProfileQueryDto;
import com.github.mjeanroy.mongohero.core.model.ProfileQuery;
import org.springframework.stereotype.Component;

@Component
public class ProfileQueryDtoMapper extends AbstractDtoMapper<ProfileQueryDto, ProfileQuery> {

	@Override
	ProfileQueryDto doMap(ProfileQuery query) {
		ProfileQueryDto dto = new ProfileQueryDto();
		dto.setOp(query.getOp());
		dto.setNs(query.getNs());
		dto.setDocsExamined(query.getDocsExamined());
		dto.setKeysExamined(query.getKeysExamined());
		dto.setKeyUpdates(query.getKeyUpdates());
		dto.setMillis(query.getMillis());
		dto.setNreturned(query.getNreturned());
		dto.setNumYield(query.getNumYield());
		dto.setHasSortStage(query.isHasSortStage());
		dto.setWriteConflicts(query.getWriteConflicts());
		dto.setQuery(query.getQuery());
		dto.setCommand(query.getCommand());
		dto.setUser(query.getUser());
		return dto;
	}
}
