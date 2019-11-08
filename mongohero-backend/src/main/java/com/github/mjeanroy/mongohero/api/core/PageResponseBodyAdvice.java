package com.github.mjeanroy.mongohero.api.core;

import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@ControllerAdvice
class PageResponseBodyAdvice implements ResponseBodyAdvice {

	@Override
	public boolean supports(MethodParameter returnType, Class converterType) {
		return returnType.getParameterType() == PageResponse.class;
	}

	@Override
	public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
		PageResponse pageResponse = (PageResponse) body;

		HttpHeaders headers = response.getHeaders();
		headers.add("X-Total", String.valueOf(pageResponse.getTotal()));
		headers.add("X-Page", String.valueOf(pageResponse.getPage()));
		headers.add("X-Page-Size", String.valueOf(pageResponse.getPageSize()));

		return pageResponse.getBody();
	}
}
