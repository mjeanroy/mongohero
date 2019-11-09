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

package com.github.mjeanroy.mongohero.api.controllers;

import com.github.mjeanroy.mongohero.core.exceptions.ReplicationDisabledException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@ControllerAdvice
class ErrorHandler {

	private final ErrorAttributes errorAttributes;

	@Autowired
	ErrorHandler(ErrorAttributes errorAttributes) {
		this.errorAttributes = errorAttributes;
	}

	@ExceptionHandler(ReplicationDisabledException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ResponseBody
	CustomError onReplicationDisabledException(HttpServletRequest webRequest, ReplicationDisabledException ex) {
		return new CustomError(
				HttpStatus.NOT_FOUND.value(),
				ex.getMessage(),
				webRequest.getServletPath()
		);
	}

	private static class CustomError {
		private final int status;
		private final String message;
		private final String path;
		private final Date timestamp;

		private CustomError(int status, String message, String path) {
			this.status = status;
			this.message = message;
			this.path = path;
			this.timestamp = new Date();
		}

		public int getStatus() {
			return status;
		}

		public String getMessage() {
			return message;
		}

		public String getPath() {
			return path;
		}

		public Date getTimestamp() {
			return timestamp;
		}
	}
}
