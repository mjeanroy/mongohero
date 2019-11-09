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

package com.github.mjeanroy.mongohero.webapp;

import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class WebAppConfiguration implements WebMvcConfigurer {

	private static final String NOT_FOUND_INTERNAL_PATH = "/notFound";
	private static final Map<String, Integer> staticResources = new HashMap<String, Integer>() {{
		put("html", 0);
		put("js", 36000);
		put("css", 3600);
		put("ttf", 3600);
		put("eot", 3600);
		put("woff", 3600);
		put("woff2", 3600);
		put("svg", 3600);
	}};

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		staticResources.forEach((key, value) ->
				addStaticResources(registry, key, value)
		);
	}

	private static void addStaticResources(ResourceHandlerRegistry registry, String extension, int cachePeriod) {
		registry.addResourceHandler("/*." + extension)
				.addResourceLocations("classpath:/")
				.setCachePeriod(cachePeriod)
				.resourceChain(true)
				.addResolver(new PathResourceResolver());
	}

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController(NOT_FOUND_INTERNAL_PATH).setStatusCode(HttpStatus.OK).setViewName("forward:/index.html");
	}

	@Bean
	public WebServerFactoryCustomizer<ConfigurableServletWebServerFactory> containerCustomizer() {
		return container -> {
			container.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND, NOT_FOUND_INTERNAL_PATH));
		};
	}
}
