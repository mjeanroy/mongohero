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

import com.github.mjeanroy.mongohero.api.dto.ReplicationStatusDto;
import com.github.mjeanroy.mongohero.core.model.ReplicationStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ReplicationStatusDtoMapper extends AbstractDtoMapper<ReplicationStatusDto, ReplicationStatus> {

	private final ReplicationMemberDtoMapper replicationMemberDtoMapper;
	private final ReplicationStateDtoMapper replicationStateDtoMapper;

	@Autowired
	ReplicationStatusDtoMapper(
			ReplicationMemberDtoMapper replicationMemberDtoMapper,
			ReplicationStateDtoMapper replicationStateDtoMapper) {

		this.replicationMemberDtoMapper = replicationMemberDtoMapper;
		this.replicationStateDtoMapper = replicationStateDtoMapper;
	}

	@Override
	ReplicationStatusDto doMap(ReplicationStatus input) {
		ReplicationStatusDto dto = new ReplicationStatusDto();
		dto.setName(input.getSet());
		dto.setHeartbeatIntervalMillis(input.getHeartbeatIntervalMillis());
		dto.setDate(input.getDate());
		dto.setMajorityVoteCount(input.getMajorityVoteCount());
		dto.setWriteMajorityCount(input.getWriteMajorityCount());
		dto.setTerm(input.getTerm());
		dto.setMyState(
				replicationStateDtoMapper.map(input.getMyState())
		);

		dto.setMembers(
				replicationMemberDtoMapper.mapToList(input.getMembers())
		);

		return dto;
	}
}
