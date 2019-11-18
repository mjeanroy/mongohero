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

import com.github.mjeanroy.mongohero.core.query.Page;
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
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PageArgumentResolverTest {

	private PageArgumentResolver resolver;

	@BeforeEach
	void setUp() {
		resolver = new PageArgumentResolver();
	}

	@Test
	void it_should_check_if_parameter_is_supported() throws Exception {
		Method method = PageArgumentResolverTest.class.getDeclaredMethod("method_with_default_page", Page.class, HttpServletRequest.class);
		assertThat(resolver.supportsParameter(new MethodParameter(method, 0))).isTrue();
		assertThat(resolver.supportsParameter(new MethodParameter(method, 1))).isFalse();
	}

	@Test
	void it_should_create_page_with_default_values() throws Exception {
		Method method = PageArgumentResolverTest.class.getDeclaredMethod("method_with_default_page", Page.class, HttpServletRequest.class);
		int parameterIndex = 0;
		MethodParameter methodParameter = new MethodParameter(method, parameterIndex);

		ModelAndViewContainer mvc = givenModelAndViewContainer();
		NativeWebRequest webRequest = givenNativeWebRequest();
		WebDataBinderFactory webDataBinderFactory = givenWebDataBinderFactory();

		Object result = resolver.resolveArgument(methodParameter, mvc, webRequest, webDataBinderFactory);

		assertThat(result).isInstanceOf(Page.class);

		Page page = (Page) result;
		assertThat(page).isNotNull();
		assertThat(page.getPage()).isEqualTo(1);
		assertThat(page.getPageSize()).isEqualTo(50);
		assertThat(page.getOffset()).isZero();
	}

	@Test
	void it_should_create_page_with_default_annotation_values() throws Exception {
		Method method = PageArgumentResolverTest.class.getDeclaredMethod("method_with_annotation_value", Page.class);
		int parameterIndex = 0;
		MethodParameter methodParameter = new MethodParameter(method, parameterIndex);

		ModelAndViewContainer mvc = givenModelAndViewContainer();
		NativeWebRequest webRequest = givenNativeWebRequest();
		WebDataBinderFactory webDataBinderFactory = givenWebDataBinderFactory();

		Object result = resolver.resolveArgument(methodParameter, mvc, webRequest, webDataBinderFactory);

		assertThat(result).isInstanceOf(Page.class);

		Page page = (Page) result;
		assertThat(page).isNotNull();
		assertThat(page.getPage()).isEqualTo(2);
		assertThat(page.getPageSize()).isEqualTo(10);
		assertThat(page.getOffset()).isEqualTo(10);
	}

	@Test
	void it_should_create_page_with_default_request_params() throws Exception {
		Method method = PageArgumentResolverTest.class.getDeclaredMethod("method_with_annotation_value", Page.class);
		int parameterIndex = 0;
		MethodParameter methodParameter = new MethodParameter(method, parameterIndex);

		ModelAndViewContainer mvc = givenModelAndViewContainer();
		NativeWebRequest webRequest = givenNativeWebRequest("5", "10");
		WebDataBinderFactory webDataBinderFactory = givenWebDataBinderFactory();

		Object result = resolver.resolveArgument(methodParameter, mvc, webRequest, webDataBinderFactory);

		assertThat(result).isInstanceOf(Page.class);

		Page page = (Page) result;
		assertThat(page).isNotNull();
		assertThat(page.getPage()).isEqualTo(5);
		assertThat(page.getPageSize()).isEqualTo(10);
		assertThat(page.getOffset()).isEqualTo(40);
	}

	@Test
	void it_should_create_page_with_default_request_param_and_default_page_size() throws Exception {
		Method method = PageArgumentResolverTest.class.getDeclaredMethod("method_with_default_page", Page.class, HttpServletRequest.class);
		int parameterIndex = 0;
		MethodParameter methodParameter = new MethodParameter(method, parameterIndex);

		ModelAndViewContainer mvc = givenModelAndViewContainer();
		NativeWebRequest webRequest = givenNativeWebRequest("5", null);
		WebDataBinderFactory webDataBinderFactory = givenWebDataBinderFactory();

		Object result = resolver.resolveArgument(methodParameter, mvc, webRequest, webDataBinderFactory);

		assertThat(result).isInstanceOf(Page.class);

		Page page = (Page) result;
		assertThat(page).isNotNull();
		assertThat(page.getPage()).isEqualTo(5);
		assertThat(page.getPageSize()).isEqualTo(50);
		assertThat(page.getOffset()).isEqualTo(200);
	}

	@Test
	void it_should_create_page_with_default_request_param_and_default_page() throws Exception {
		Method method = PageArgumentResolverTest.class.getDeclaredMethod("method_with_default_page", Page.class, HttpServletRequest.class);
		int parameterIndex = 0;
		MethodParameter methodParameter = new MethodParameter(method, parameterIndex);

		ModelAndViewContainer mvc = givenModelAndViewContainer();
		NativeWebRequest webRequest = givenNativeWebRequest(null, "10");
		WebDataBinderFactory webDataBinderFactory = givenWebDataBinderFactory();

		Object result = resolver.resolveArgument(methodParameter, mvc, webRequest, webDataBinderFactory);

		assertThat(result).isInstanceOf(Page.class);

		Page page = (Page) result;
		assertThat(page).isNotNull();
		assertThat(page.getPage()).isEqualTo(1);
		assertThat(page.getPageSize()).isEqualTo(10);
		assertThat(page.getOffset()).isEqualTo(0);
	}

	@Test
	void it_should_create_page_with_trimmed_page() throws Exception {
		Method method = PageArgumentResolverTest.class.getDeclaredMethod("method_with_default_page", Page.class, HttpServletRequest.class);
		int parameterIndex = 0;
		MethodParameter methodParameter = new MethodParameter(method, parameterIndex);

		ModelAndViewContainer mvc = givenModelAndViewContainer();
		NativeWebRequest webRequest = givenNativeWebRequest(" 5 ", null);
		WebDataBinderFactory webDataBinderFactory = givenWebDataBinderFactory();

		Object result = resolver.resolveArgument(methodParameter, mvc, webRequest, webDataBinderFactory);

		assertThat(result).isInstanceOf(Page.class);

		Page page = (Page) result;
		assertThat(page).isNotNull();
		assertThat(page.getPage()).isEqualTo(5);
		assertThat(page.getPageSize()).isEqualTo(50);
		assertThat(page.getOffset()).isEqualTo(200);
	}

	@Test
	void it_should_create_page_with_trimmed_page_size() throws Exception {
		Method method = PageArgumentResolverTest.class.getDeclaredMethod("method_with_default_page", Page.class, HttpServletRequest.class);
		int parameterIndex = 0;
		MethodParameter methodParameter = new MethodParameter(method, parameterIndex);

		ModelAndViewContainer mvc = givenModelAndViewContainer();
		NativeWebRequest webRequest = givenNativeWebRequest(null, " 10 ");
		WebDataBinderFactory webDataBinderFactory = givenWebDataBinderFactory();

		Object result = resolver.resolveArgument(methodParameter, mvc, webRequest, webDataBinderFactory);

		assertThat(result).isInstanceOf(Page.class);

		Page page = (Page) result;
		assertThat(page).isNotNull();
		assertThat(page.getPage()).isEqualTo(1);
		assertThat(page.getPageSize()).isEqualTo(10);
		assertThat(page.getOffset()).isEqualTo(0);
	}

	@Test
	void it_should_fail_to_parse_invalid_page_number() throws Exception {
		Method method = PageArgumentResolverTest.class.getDeclaredMethod("method_with_default_page", Page.class, HttpServletRequest.class);
		int parameterIndex = 0;
		MethodParameter methodParameter = new MethodParameter(method, parameterIndex);

		ModelAndViewContainer mvc = givenModelAndViewContainer();
		NativeWebRequest webRequest = givenNativeWebRequest("test", null);
		WebDataBinderFactory webDataBinderFactory = givenWebDataBinderFactory();

		assertThatThrownBy(() -> resolver.resolveArgument(methodParameter, mvc, webRequest, webDataBinderFactory))
				.isInstanceOf(IllegalArgumentException.class)
				.hasMessage("Cannot parse page parameter: test");
	}

	@Test
	void it_should_fail_to_parse_invalid_page_size_number() throws Exception {
		Method method = PageArgumentResolverTest.class.getDeclaredMethod("method_with_default_page", Page.class, HttpServletRequest.class);
		int parameterIndex = 0;
		MethodParameter methodParameter = new MethodParameter(method, parameterIndex);

		ModelAndViewContainer mvc = givenModelAndViewContainer();
		NativeWebRequest webRequest = givenNativeWebRequest(null, "test");
		WebDataBinderFactory webDataBinderFactory = givenWebDataBinderFactory();

		assertThatThrownBy(() -> resolver.resolveArgument(methodParameter, mvc, webRequest, webDataBinderFactory))
				.isInstanceOf(IllegalArgumentException.class)
				.hasMessage("Cannot parse page size parameter: test");
	}

	private static HttpServletRequest givenHttpServletRequest() {
		return new MockHttpServletRequest();
	}

	private static NativeWebRequest givenNativeWebRequest() {
		return new ServletWebRequest(givenHttpServletRequest());
	}

	private static NativeWebRequest givenNativeWebRequest(String page, String pageSize) {
		return new ServletWebRequest(givenHttpServletRequest(page, pageSize));
	}

	private static HttpServletRequest givenHttpServletRequest(String page, String pageSize) {
		MockHttpServletRequest request = new MockHttpServletRequest();

		if (page != null) {
			request.addParameter("page", String.valueOf(page));
		}

		if (pageSize != null) {
			request.addParameter("pageSize", String.valueOf(pageSize));
		}

		return request;
	}

	private static WebDataBinderFactory givenWebDataBinderFactory() {
		return new DefaultDataBinderFactory(null);
	}

	private ModelAndViewContainer givenModelAndViewContainer() {
		return new ModelAndViewContainer();
	}

	@SuppressWarnings("unused")
	void method_with_default_page(@PageParam Page page, HttpServletRequest request) {
	}

	@SuppressWarnings("unused")
	void method_with_annotation_value(@PageParam(defaultPage = 2, defaultPageSize = 10) Page page) {
	}
}
