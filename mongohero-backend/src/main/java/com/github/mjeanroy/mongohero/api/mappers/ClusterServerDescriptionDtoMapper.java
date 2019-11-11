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

import com.github.mjeanroy.mongohero.api.dto.ClusterServerDescriptionDto;
import com.mongodb.connection.ServerDescription;
import com.mongodb.connection.ServerType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class ClusterServerDescriptionDtoMapper extends AbstractDtoMapper<ClusterServerDescriptionDto, ServerDescription> {

	private final ClusterServerAddressDtoMapper clusterServerAddressDtoMapper;

	@Autowired
	ClusterServerDescriptionDtoMapper(ClusterServerAddressDtoMapper clusterServerAddressDtoMapper) {
		this.clusterServerAddressDtoMapper = clusterServerAddressDtoMapper;
	}

	@Override
	ClusterServerDescriptionDto doMap(ServerDescription input) {
		ClusterServerDescriptionDto dto = new ClusterServerDescriptionDto();
		dto.setRoundTripTimeNanos(input.getRoundTripTimeNanos());
		dto.setMaxDocumentSize(input.getMaxDocumentSize());
		dto.setCanonicalAddress(input.getCanonicalAddress());
		dto.setHosts(new ArrayList<>(input.getHosts()));
		dto.setArbiters(new ArrayList<>(input.getArbiters()));
		dto.setPassives(new ArrayList<>(input.getPassives()));
		dto.setPrimary(input.getPrimary());

		ServerType type = input.getType();
		if (type != null) {
			dto.setType(type.name());
		}

		dto.setAddress(
				clusterServerAddressDtoMapper.map(input.getAddress())
		);

		return dto;
	}
}
