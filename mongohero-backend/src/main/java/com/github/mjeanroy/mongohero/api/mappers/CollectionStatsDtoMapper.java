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

import com.github.mjeanroy.mongohero.api.dto.CollectionStatsDto;
import com.github.mjeanroy.mongohero.api.dto.IndexSizeDto;
import com.github.mjeanroy.mongohero.core.model.CollectionStats;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class CollectionStatsDtoMapper extends AbstractDtoMapper<CollectionStatsDto, CollectionStats> {

    @Override
    CollectionStatsDto doMap(CollectionStats collectionStats) {
        CollectionStatsDto dto = new CollectionStatsDto();
        dto.setNs(collectionStats.getNs());
        dto.setAvgObjSize(collectionStats.getAvgObjSize());
        dto.setSize(collectionStats.getSize());
        dto.setStorageSize(collectionStats.getStorageSize());
        dto.setCount(collectionStats.getCount());
        dto.setNindexes(collectionStats.getNindexes());
        dto.setTotalIndexSize(collectionStats.getTotalIndexSize());
        dto.setCapped(collectionStats.isCapped());
        dto.setIndexSizes(
                toIndexSizeDtos(collectionStats.getIndexSizes())
        );

        return dto;
    }

    private List<IndexSizeDto> toIndexSizeDtos(Map<String, Integer> indexSizes) {
        return indexSizes.entrySet().stream().map(this::toIndexSizeDto).collect(Collectors.toList());
    }

    private IndexSizeDto toIndexSizeDto(Map.Entry<String, Integer> entry) {
        IndexSizeDto indexSizeDto = new IndexSizeDto();
        indexSizeDto.setName(entry.getKey());
        indexSizeDto.setSize(entry.getValue());
        return indexSizeDto;
    }
}
