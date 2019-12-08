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

package com.github.mjeanroy.mongohero.core.model;

import com.github.mjeanroy.mongohero.core.tests.builders.IndexStatBuilder;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

class IndexStatModel {

	@Test
	void it_should_merge_index_stat() {
		Date d1 = new Date();
		Date d2 = new Date();
		IndexStat i1 = new IndexStatBuilder().withName("_id").withAccesses(10, d1).build();
		IndexStat i2 = new IndexStatBuilder().withName("_id").withAccesses(5, d2).build();

		IndexStat result = i1.merge(i2);

		assertIndexStat(result, "_id", 15L, d1);
		assertIndexStat(i1, "_id", 10L, d1);
		assertIndexStat(i2, "_id", 5L, d2);
	}

	private static void assertIndexStat(IndexStat result, String name, long ops, Date since) {
		assertThat(result).isNotNull();
		assertThat(result.getName()).isEqualTo(name);
		assertThat(result.getAccesses().getOps()).isEqualTo(ops);
		assertThat(result.getAccesses().getSince()).isEqualTo(since);
	}
}
