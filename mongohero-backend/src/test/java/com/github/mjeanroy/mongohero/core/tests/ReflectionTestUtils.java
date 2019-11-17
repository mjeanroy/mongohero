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

package com.github.mjeanroy.mongohero.core.tests;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Optional;

public final class ReflectionTestUtils {

	private ReflectionTestUtils() {
	}

	/**
	 * Instantiate new object of given class using empty constructor (must be, at least, package private).
	 *
	 * @param klass The klass.
	 * @param <T>   Type of object being instantiated.
	 * @return The new instance.
	 */
	public static <T> T instantiate(Class<T> klass) {
		try {
			Constructor<T> ctor = klass.getDeclaredConstructor();
			ctor.setAccessible(true);
			return ctor.newInstance();
		}
		catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException ex) {
			throw new AssertionError(ex);
		}
	}

	/**
	 * Write on private field on given target object.
	 *
	 * @param target Target object.
	 * @param name   Field name.
	 * @param value  Field value to write.
	 * @param <T>    Type of field value.
	 */
	public static <T> void writePrivateField(Object target, String name, T value) {
		try {
			Class<?> targetClass = target.getClass();
			Field field = findDeclaredFieldOrFail(targetClass, name);
			field.setAccessible(true);
			field.set(target, value);
		}
		catch (IllegalAccessException ex) {
			throw new AssertionError(ex);
		}
	}

	/**
	 * Find declared field (may be private field) on given class or one of its superclass and
	 * fail with {@link AssertionError} if given field does not exist..
	 *
	 * @param klass The class.
	 * @param name The field name.
	 * @return The given field.
	 */
	private static <T> Field findDeclaredFieldOrFail(Class<T> klass, String name) {
		return findDeclaredField(klass, name).orElseThrow(() -> (
				new AssertionError("Cannof find " + klass + " # " + name)
		));
	}

	/**
	 * Find declared field (may be private field) on given class or one of its superclass.
	 *
	 * @param klass The class.
	 * @param name The field name.
	 * @return The given field if it exists, empty results otherwise.
	 */
	private static Optional<Field> findDeclaredField(Class<?> klass, String name) {
		Class<?> current = klass;
		while (current != null) {
			Optional<Field> maybeField = getDeclaredField(klass, name);
			if (maybeField.isPresent()) {
				return maybeField;
			}

			current = current.getSuperclass();
		}

		return Optional.empty();
	}

	/**
	 * Find declared field (may be private field) on given class.
	 *
	 * @param klass The class.
	 * @param name The field name.
	 * @return The given field if it exists, empty results otherwise.
	 */
	private static Optional<Field> getDeclaredField(Class<?> klass, String name) {
		try {
			return Optional.of(klass.getDeclaredField(name));
		}
		catch (NoSuchFieldException ex) {
			return Optional.empty();
		}
	}
}
