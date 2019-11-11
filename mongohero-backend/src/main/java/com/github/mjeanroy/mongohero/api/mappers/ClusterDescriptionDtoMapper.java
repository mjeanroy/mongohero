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

import com.github.mjeanroy.mongohero.api.dto.ClusterDescriptionDto;
import com.mongodb.connection.ClusterConnectionMode;
import com.mongodb.connection.ClusterDescription;
import com.mongodb.connection.ClusterType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ClusterDescriptionDtoMapper extends AbstractDtoMapper<ClusterDescriptionDto, ClusterDescription> {

	private final ClusterSettingsDtoMapper clusterSettingsDtoMapper;
	private final ClusterServerDescriptionDtoMapper clusterServerDescriptionDtoMapper;

	@Autowired
	ClusterDescriptionDtoMapper(
			ClusterSettingsDtoMapper clusterSettingsDtoMapper,
			ClusterServerDescriptionDtoMapper clusterServerDescriptionDtoMapper) {

		this.clusterSettingsDtoMapper = clusterSettingsDtoMapper;
		this.clusterServerDescriptionDtoMapper = clusterServerDescriptionDtoMapper;
	}

	@Override
	ClusterDescriptionDto doMap(ClusterDescription input) {
		ClusterDescriptionDto dto = new ClusterDescriptionDto();

		ClusterConnectionMode connectionMode = input.getConnectionMode();
		if (connectionMode != null) {
			dto.setConnectionMode(connectionMode.name());
		}

		ClusterType type = input.getType();
		if (type != null) {
			dto.setType(type.name());
		}

		dto.setSettings(
				clusterSettingsDtoMapper.map(input.getClusterSettings())
		);

		dto.setServerDescriptions(
				clusterServerDescriptionDtoMapper.mapToList(input.getServerDescriptions())
		);

		return dto;
	}
}
