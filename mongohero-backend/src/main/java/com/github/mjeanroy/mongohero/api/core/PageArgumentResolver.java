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

package com.github.mjeanroy.mongohero.api.core;

import com.github.mjeanroy.mongohero.core.query.Page;
import com.github.mjeanroy.mongohero.core.query.Sort;
import com.github.mjeanroy.mongohero.core.query.Sort.Order;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class PageArgumentResolver implements HandlerMethodArgumentResolver {

	@Override
	public boolean supportsParameter(MethodParameter methodParameter) {
		return methodParameter.getParameter().getType() == Page.class;
	}

	@Override
	public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
		String pageParameter = nativeWebRequest.getParameter("page");
		String pageSizeParameter = nativeWebRequest.getParameter("pageSize");
		PageParam pageParam = methodParameter.getParameterAnnotation(PageParam.class);

		if (StringUtils.isEmpty(pageParameter)) {
			pageParameter = pageParam == null ? "1" : String.valueOf(pageParam.defaultPage());
		}

		if (StringUtils.isEmpty(pageSizeParameter)) {
			pageSizeParameter = pageParam == null ? "50" : String.valueOf(pageParam.defaultPageSize());
		}

		if (!NumberUtils.isDigits(pageParameter)) {
			throw new IllegalArgumentException("Cannot parse page parameter: " + pageParameter);
		}

		if (!NumberUtils.isDigits(pageSizeParameter)) {
			throw new IllegalArgumentException("Cannot parse page size parameter: " + pageSizeParameter);
		}

		return new Page(Integer.parseInt(pageParameter), Integer.parseInt(pageSizeParameter));
	}

	private static Sort parse(String param) {
		char firstChar = param.charAt(0);
		Order order = firstChar == '-' ? Order.DESC : Order.ASC;
		String name = firstChar == '-' || firstChar == '+' ? param.substring(1) : param;
		return new Sort(name, order);
	}

}
