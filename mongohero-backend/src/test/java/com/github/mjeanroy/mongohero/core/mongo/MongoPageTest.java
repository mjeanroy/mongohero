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

package com.github.mjeanroy.mongohero.core.mongo;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.bson.Document;
import org.junit.jupiter.api.Test;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class MongoPageTest {

	@Test
	void it_should_create_page() {
		final Stream<Document> results = Stream.empty();
		final int total = 10;

		final MongoPage page = MongoPage.of(results, total);

		assertThat(page).isNotNull();
		assertThat(page.getResults()).isSameAs(results);
		assertThat(page.getTotal()).isEqualTo(total);
	}

	@Test
	void it_should_implement_equals_and_hash_code() {
		EqualsVerifier.forClass(MongoPage.class).verify();
	}

	@Test
	void it_should_implement_to_string() {
		final Stream<Document> results = Stream.empty();
		final int total = 10;
		final MongoPage page = MongoPage.of(results, total);

		assertThat(page).hasToString(
				"com.github.mjeanroy.mongohero.core.mongo.MongoPage@" + Integer.toHexString(System.identityHashCode(page)) + "[" +
						"results=" + results.toString() + "," +
						"total=10" +
				"]"
		);
	}
}
