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

package com.github.mjeanroy.mongohero.api.core;

import com.github.mjeanroy.mongohero.core.query.Sort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.MethodParameter;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.bind.support.DefaultDataBinderFactory;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

import static org.assertj.core.api.Assertions.assertThat;

class SortArgumentResolverTest {

	private SortArgumentResolver resolver;

	@BeforeEach
	void setUp() {
		resolver = new SortArgumentResolver();
	}

	@Test
	void it_should_check_if_parameter_is_supported() throws Exception {
		Method method = SortArgumentResolverTest.class.getDeclaredMethod("method_with_default_sort", Sort.class, HttpServletRequest.class);
		assertThat(resolver.supportsParameter(new MethodParameter(method, 0))).isTrue();
		assertThat(resolver.supportsParameter(new MethodParameter(method, 1))).isFalse();
	}

	@Test
	void it_should_create_sort_with_default_values() throws Exception {
		Method method = SortArgumentResolverTest.class.getDeclaredMethod("method_with_default_sort", Sort.class, HttpServletRequest.class);
		int parameterIndex = 0;
		MethodParameter methodParameter = new MethodParameter(method, parameterIndex);

		ModelAndViewContainer mvc = givenModelAndViewContainer();
		NativeWebRequest webRequest = givenNativeWebRequest();
		WebDataBinderFactory webDataBinderFactory = givenWebDataBinderFactory();

		Object result = resolver.resolveArgument(methodParameter, mvc, webRequest, webDataBinderFactory);

		assertThat(result).isInstanceOf(Sort.class);

		Sort sort = (Sort) result;
		assertThat(sort).isNotNull();
		assertThat(sort.getName()).isEqualTo("id");
		assertThat(sort.isAsc()).isTrue();
		assertThat(sort.isDesc()).isFalse();
	}

	@Test
	void it_should_create_sort_with_non_default_values() throws Exception {
		Method method = SortArgumentResolverTest.class.getDeclaredMethod("method_with_annotation_value", Sort.class);
		int parameterIndex = 0;
		MethodParameter methodParameter = new MethodParameter(method, parameterIndex);

		ModelAndViewContainer mvc = givenModelAndViewContainer();
		NativeWebRequest webRequest = givenNativeWebRequest();
		WebDataBinderFactory webDataBinderFactory = givenWebDataBinderFactory();

		Object result = resolver.resolveArgument(methodParameter, mvc, webRequest, webDataBinderFactory);

		assertThat(result).isInstanceOf(Sort.class);

		Sort sort = (Sort) result;
		assertThat(sort).isNotNull();
		assertThat(sort.getName()).isEqualTo("id");
		assertThat(sort.isAsc()).isFalse();
		assertThat(sort.isDesc()).isTrue();
	}

	@Test
	void it_should_create_sort_with_request_param_without_order_char() throws Exception {
		Method method = SortArgumentResolverTest.class.getDeclaredMethod("method_with_annotation_value", Sort.class);
		int parameterIndex = 0;
		MethodParameter methodParameter = new MethodParameter(method, parameterIndex);

		ModelAndViewContainer mvc = givenModelAndViewContainer();
		NativeWebRequest webRequest = givenNativeWebRequest("name");
		WebDataBinderFactory webDataBinderFactory = givenWebDataBinderFactory();

		Object result = resolver.resolveArgument(methodParameter, mvc, webRequest, webDataBinderFactory);

		assertThat(result).isInstanceOf(Sort.class);

		Sort sort = (Sort) result;
		assertThat(sort).isNotNull();
		assertThat(sort.getName()).isEqualTo("name");
		assertThat(sort.isAsc()).isTrue();
		assertThat(sort.isDesc()).isFalse();
	}

	@Test
	void it_should_create_sort_with_request_param_without_asc_order() throws Exception {
		Method method = SortArgumentResolverTest.class.getDeclaredMethod("method_with_annotation_value", Sort.class);
		int parameterIndex = 0;
		MethodParameter methodParameter = new MethodParameter(method, parameterIndex);

		ModelAndViewContainer mvc = givenModelAndViewContainer();
		NativeWebRequest webRequest = givenNativeWebRequest("+name");
		WebDataBinderFactory webDataBinderFactory = givenWebDataBinderFactory();

		Object result = resolver.resolveArgument(methodParameter, mvc, webRequest, webDataBinderFactory);

		assertThat(result).isInstanceOf(Sort.class);

		Sort sort = (Sort) result;
		assertThat(sort).isNotNull();
		assertThat(sort.getName()).isEqualTo("name");
		assertThat(sort.isAsc()).isTrue();
		assertThat(sort.isDesc()).isFalse();
	}

	@Test
	void it_should_create_sort_with_request_param_without_desc_order() throws Exception {
		Method method = SortArgumentResolverTest.class.getDeclaredMethod("method_with_annotation_value", Sort.class);
		int parameterIndex = 0;
		MethodParameter methodParameter = new MethodParameter(method, parameterIndex);

		ModelAndViewContainer mvc = givenModelAndViewContainer();
		NativeWebRequest webRequest = givenNativeWebRequest("-name");
		WebDataBinderFactory webDataBinderFactory = givenWebDataBinderFactory();

		Object result = resolver.resolveArgument(methodParameter, mvc, webRequest, webDataBinderFactory);

		assertThat(result).isInstanceOf(Sort.class);

		Sort sort = (Sort) result;
		assertThat(sort).isNotNull();
		assertThat(sort.getName()).isEqualTo("name");
		assertThat(sort.isAsc()).isFalse();
		assertThat(sort.isDesc()).isTrue();
	}

	@Test
	void it_should_create_sort_with_trimmed_parameter() throws Exception {
		Method method = SortArgumentResolverTest.class.getDeclaredMethod("method_with_annotation_value", Sort.class);
		int parameterIndex = 0;
		MethodParameter methodParameter = new MethodParameter(method, parameterIndex);

		ModelAndViewContainer mvc = givenModelAndViewContainer();
		NativeWebRequest webRequest = givenNativeWebRequest(" -name ");
		WebDataBinderFactory webDataBinderFactory = givenWebDataBinderFactory();

		Object result = resolver.resolveArgument(methodParameter, mvc, webRequest, webDataBinderFactory);

		assertThat(result).isInstanceOf(Sort.class);

		Sort sort = (Sort) result;
		assertThat(sort).isNotNull();
		assertThat(sort.getName()).isEqualTo("name");
		assertThat(sort.isAsc()).isFalse();
		assertThat(sort.isDesc()).isTrue();
	}

	private static HttpServletRequest givenHttpServletRequest() {
		return new MockHttpServletRequest();
	}

	private static NativeWebRequest givenNativeWebRequest() {
		return new ServletWebRequest(givenHttpServletRequest());
	}

	private static NativeWebRequest givenNativeWebRequest(String sortParam) {
		return new ServletWebRequest(givenHttpServletRequest(sortParam));
	}

	private static HttpServletRequest givenHttpServletRequest(String sortParam) {
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.addParameter("sort", sortParam);
		return request;
	}

	private static WebDataBinderFactory givenWebDataBinderFactory() {
		return new DefaultDataBinderFactory(null);
	}

	private ModelAndViewContainer givenModelAndViewContainer() {
		return new ModelAndViewContainer();
	}

	@SuppressWarnings("unused")
	void method_with_default_sort(@SortParam(defaultName = "id") Sort sort, HttpServletRequest request) {
	}

	@SuppressWarnings("unused")
	void method_with_annotation_value(@SortParam(defaultName = "id", defaultOrder = Sort.Order.DESC) Sort sort) {
	}
}
