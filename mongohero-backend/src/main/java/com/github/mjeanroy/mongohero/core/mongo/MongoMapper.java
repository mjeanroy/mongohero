/**
 * The MIT License (MIT)
 * <p>
 * Copyright (c) 2019 Mickael Jeanroy
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.github.mjeanroy.mongohero.core.mongo;

import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.bson.Document;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class MongoMapper {

	public <T> T map(Document document, Class<T> klass) {
		if (document == null) {
			return null;
		}

		if (Map.class.isAssignableFrom(klass)) {
			return asMap(document);
		}

		T instance = newInstance(klass);

		getAllFields(klass).forEach(field -> {
			writeField(document, field, instance);
		});

		return instance;
	}

	public <T> Stream<T> mapToStream(Iterable<Document> documents, Class<T> klass) {
		return StreamSupport.stream(documents.spliterator(), false).map(document -> map(document, klass));
	}

	@SuppressWarnings("unchecked")
	private static <T> T asMap(Document document) {
		return (T) new LinkedHashMap<>(document);
	}

	private <T> void writeField(Document document, Field field, T instance) {
		try {
			String name = field.getName();
			Object value = document.get(name);
			if (value == null) {
				return;
			}

			if (value instanceof Document) {
				value = transformToObject(field, (Document) value);
			}
			else if (value instanceof List) {
				value = transformToList((List<Document>) value, (ParameterizedType) field.getGenericType());
			}

			FieldUtils.writeDeclaredField(instance, name, value, true);
		}
		catch (Exception ex) {
			throw new MongoMapperException(ex);
		}
	}

	private List<Object> transformToList(List<Document> value, ParameterizedType type) {
		Class<?> targetClass = (Class<?>) type.getActualTypeArguments()[0];
		if (ClassUtils.isPrimitiveOrWrapper(targetClass) || targetClass == String.class) {
			return new ArrayList<>(value);
		}

		return value.stream().map(doc -> map(doc, targetClass)).collect(Collectors.toList());
	}

	private Object transformToObject(Field field, Document value) {
		return map(value, field.getType());
	}

	private static <T> Stream<Field> getAllFields(Class<T> klass) {
		return Arrays.stream(FieldUtils.getAllFields(klass));
	}

	private static <T> T newInstance(Class<T> klass) {
		Constructor<T> ctor = null;
		boolean wasAccessible = true;

		try {
			ctor = klass.getDeclaredConstructor();
			wasAccessible = ctor.isAccessible();

			if (!wasAccessible) {
				ctor.setAccessible(true);
			}

			return ctor.newInstance();
		}
		catch (Exception ex) {
			throw new MongoMapperException(ex);
		}
		finally {
			if (!wasAccessible) {
				ctor.setAccessible(false);
			}
		}
	}
}
