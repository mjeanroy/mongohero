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

import com.github.mjeanroy.mongohero.api.dto.CollectionDto;
import com.github.mjeanroy.mongohero.core.model.Collection;
import com.github.mjeanroy.mongohero.core.services.CollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CollectionDtoMapper extends AbstractDtoMapper<CollectionDto, Collection> {

	private final CollectionService collectionService;
	private final CollectionStatsDtoMapper collectionStatsDtoMapper;

	@Autowired
	CollectionDtoMapper(
			CollectionService collectionService,
			CollectionStatsDtoMapper collectionStatsDtoMapper) {

		this.collectionService = collectionService;
		this.collectionStatsDtoMapper = collectionStatsDtoMapper;
	}

	@Override
	CollectionDto doMap(Collection collection) {
		CollectionDto dto = new CollectionDto();
		dto.setName(collection.getName());
		dto.setStats(collectionStatsDtoMapper.map(
				collectionService.findStats(collection.getDatabase(), collection.getName())
		));

		return dto;
	}
}
