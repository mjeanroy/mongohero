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

package com.github.mjeanroy.mongohero.core.query;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ProfileQueryFilterTest {

	@Test
	void it_should_create_filter_with_op_field() {
		ProfileQueryFilter filters = new ProfileQueryFilter.Builder()
				.withOp("query")
				.build();

		assertThat(filters).isNotNull();
		assertThat(filters.getOp()).isEqualTo("query");
		assertThat(filters.getNsBlacklist()).isEmpty();
	}

	@Test
	void it_should_create_filter_with_ns_blacklist_field() {
		String ns = "marvels.system.profile";
		ProfileQueryFilter filters = new ProfileQueryFilter.Builder()
				.addBlacklistedNs(ns)
				.build();

		assertThat(filters).isNotNull();
		assertThat(filters.getOp()).isNull();
		assertThat(filters.getNsBlacklist()).hasSize(1).containsOnly(ns);
	}

	@Test
	void it_should_create_builder_from_filter() {
		String op1 = "query";
		String ns1 = "marvels.system.profile";
		ProfileQueryFilter f1 = new ProfileQueryFilter.Builder()
				.addBlacklistedNs(ns1)
				.withOp(op1)
				.build();

		String op2 = "command";
		String ns2 = "marvels.movies";
		ProfileQueryFilter f2 = f1.toBuilder()
				.withOp(op2)
				.addBlacklistedNs(ns2)
				.build();

		assertThat(f1).isNotNull();
		assertThat(f1.getOp()).isEqualTo("query");
		assertThat(f1.getNsBlacklist()).hasSize(1).containsOnly(ns1);

		assertThat(f2).isNotNull();
		assertThat(f2.getOp()).isEqualTo("command");
		assertThat(f2.getNsBlacklist()).hasSize(2).containsOnly(ns1, ns2);
	}

	@Test
	void it_should_implement_equals_hash_code() {
		EqualsVerifier.forClass(ProfileQueryFilter.class).verify();
	}

	@Test
	void it_should_implement_to_string() {
		ProfileQueryFilter filters = new ProfileQueryFilter.Builder()
				.addBlacklistedNs("marvels.system.profile")
				.withOp("query")
				.build();

		assertThat(filters).hasToString(
				"com.github.mjeanroy.mongohero.core.query.ProfileQueryFilter@" + Integer.toHexString(System.identityHashCode(filters)) + "[" +
						"op=query," +
						"nsBlacklist=[marvels.system.profile]" +
				"]"
		);
	}

	@Nested
	class BuilderTest {

		@Test
		void it_should_implement_equals_hash_code() {
			EqualsVerifier.forClass(ProfileQueryFilter.Builder.class)
					.suppress(Warning.NONFINAL_FIELDS)
					.verify();
		}

		@Test
		void it_should_implement_to_string() {
			ProfileQueryFilter.Builder builder = new ProfileQueryFilter.Builder()
					.addBlacklistedNs("marvels.system.profile")
					.withOp("query");

			assertThat(builder).hasToString(
					"com.github.mjeanroy.mongohero.core.query.ProfileQueryFilter$Builder@" + Integer.toHexString(System.identityHashCode(builder)) + "[" +
							"op=query," +
							"nsBlacklist=[marvels.system.profile]" +
							"]"
			);
		}
	}
}
