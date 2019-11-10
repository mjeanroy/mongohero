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

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MongoPreConditionsTest {

	@Nested
	class DatabaseNameValidity {

		@Test
		void it_should_fail_with_null() {
			assertThatThrownBy(() -> MongoPreConditions.checkDatabaseNameValidity(null))
					.isInstanceOf(IllegalMongoDatabaseNameException.class)
					.hasMessage("Database name 'null' is not a valid name")
					.hasCauseInstanceOf(IllegalArgumentException.class);
		}

		@Test
		void it_should_fail_with_empty() {
			assertThatThrownBy(() -> MongoPreConditions.checkDatabaseNameValidity(""))
					.isInstanceOf(IllegalMongoDatabaseNameException.class)
					.hasMessage("Database name '' is not a valid name")
					.hasCauseInstanceOf(IllegalArgumentException.class);
		}

		@Test
		void it_should_fail_with_invalid_characters() {
			assertThatThrownBy(() -> MongoPreConditions.checkDatabaseNameValidity("db name"))
					.isInstanceOf(IllegalMongoDatabaseNameException.class)
					.hasMessage("Database name 'db name' is not a valid name")
					.hasCauseInstanceOf(IllegalArgumentException.class);
		}

		@Test
		void it_should_returns_db_name_if_everything_is_ok() {
			assertThat(MongoPreConditions.checkDatabaseNameValidity("marvels")).isEqualTo("marvels");
		}
	}

	@Nested
	class CollectionNameValidity {

		@Test
		void it_should_fail_with_null() {
			assertThatThrownBy(() -> MongoPreConditions.checkCollectionNameValidity(null))
					.isInstanceOf(IllegalMongoCollectionNameException.class)
					.hasMessage("Collection name 'null' is not a valid name")
					.hasCauseInstanceOf(IllegalArgumentException.class);
		}

		@Test
		void it_should_fail_with_empty() {
			assertThatThrownBy(() -> MongoPreConditions.checkCollectionNameValidity(""))
					.isInstanceOf(IllegalMongoCollectionNameException.class)
					.hasMessage("Collection name '' is not a valid name")
					.hasCauseInstanceOf(IllegalArgumentException.class);
		}

		@Test
		void it_should_returns_db_name_if_everything_is_ok() {
			assertThat(MongoPreConditions.checkCollectionNameValidity("avengers")).isEqualTo("avengers");
		}
	}
}
