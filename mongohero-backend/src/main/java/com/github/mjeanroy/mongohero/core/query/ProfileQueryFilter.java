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

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

import static java.util.Collections.unmodifiableSet;

/**
 * Filter for queries on {@code "system.profiles"} collection.
 */
public final class ProfileQueryFilter {

	/**
	 * The operation, may be {@code null}.
	 *
	 * If filled, filter will be applied using strict equality.
	 */
	private final String op;

	/**
	 * A blacklist of {@code "ns"}.
	 *
	 * If filled, filter will be applied using {@code "$nin"} operator.
	 */
	private final Set<String> nsBlacklist;

	private ProfileQueryFilter(
			String op,
			Set<String> collectionsBlacklist) {

		this.op = op;
		this.nsBlacklist = new LinkedHashSet<>(collectionsBlacklist);
	}

	/**
	 * Get {@link #op}
	 *
	 * @return {@link #op}
	 */
	public String getOp() {
		return op;
	}

	/**
	 * Get {@link #nsBlacklist}
	 *
	 * @return {@link #nsBlacklist}
	 */
	public Set<String> getNsBlacklist() {
		return unmodifiableSet(nsBlacklist);
	}

	/**
	 * Create builder from given filter instance.
	 *
	 * @return The builder.
	 */
	public ProfileQueryFilter.Builder toBuilder() {
		return new ProfileQueryFilter.Builder().withOp(op).addAllBlacklistedNs(nsBlacklist);
	}

	@Override
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}

		if (o instanceof ProfileQueryFilter) {
			ProfileQueryFilter f = (ProfileQueryFilter) o;
			return Objects.equals(op, f.op) && Objects.equals(nsBlacklist, f.nsBlacklist);
		}

		return false;
	}

	@Override
	public int hashCode() {
		return Objects.hash(op, nsBlacklist);
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this)
				.append("op", op)
				.append("nsBlacklist", nsBlacklist)
				.build();
	}

	/**
	 * Builder for {@link ProfileQueryFilter}.
	 */
	public static final class Builder {

		/**
		 * The operation name.
		 *
		 * @see ProfileQueryFilter#getOp()
		 */
		private String op;

		/**
		 * A blacklist of collection that should not be returned.
		 */
		private final Set<String> nsBlacklist;

		public Builder() {
			this.nsBlacklist = new LinkedHashSet<>();
		}

		/**
		 * Set {@link #op}
		 *
		 * @param op New {@link #op}
		 * @return The builder.
		 */
		public Builder withOp(String op) {
			this.op = op;
			return this;
		}

		/**
		 * Add entry to {@link #nsBlacklist}
		 *
		 * @param ns The collection to filter.
		 * @return The builder.
		 */
		public Builder addBlacklistedNs(String ns) {
			this.nsBlacklist.add(ns);
			return this;
		}

		/**
		 * Add all entries to {@link #nsBlacklist}
		 *
		 * @param nsBlacklist The blacklist.
		 * @return The builder.
		 */
		public Builder addAllBlacklistedNs(Collection<String> nsBlacklist) {
			this.nsBlacklist.addAll(nsBlacklist);
			return this;
		}

		/**
		 * Build filters.
		 *
		 * @return Filters.
		 */
		public ProfileQueryFilter build() {
			return new ProfileQueryFilter(op, nsBlacklist);
		}

		@Override
		public boolean equals(Object o) {
			if (o == this) {
				return true;
			}

			if (o instanceof ProfileQueryFilter.Builder) {
				ProfileQueryFilter.Builder b = (ProfileQueryFilter.Builder) o;
				return Objects.equals(op, b.op) && Objects.equals(nsBlacklist, b.nsBlacklist);
			}

			return false;
		}

		@Override
		public int hashCode() {
			return Objects.hash(op, nsBlacklist);
		}

		@Override
		public String toString() {
			return new ToStringBuilder(this)
					.append("op", op)
					.append("nsBlacklist", nsBlacklist)
					.build();
		}
	}
}
