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
import com.github.mjeanroy.mongohero.core.mongo.IllegalMongoCollectionAccessException;
import com.github.mjeanroy.mongohero.core.mongo.IllegalMongoCollectionNameException;
import com.github.mjeanroy.mongohero.core.mongo.IllegalMongoDatabaseAccessException;
import com.github.mjeanroy.mongohero.core.mongo.IllegalMongoDatabaseNameException;
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
		return renderError(HttpStatus.NOT_FOUND, webRequest, ex);
	}

	@ExceptionHandler(IllegalMongoDatabaseNameException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ResponseBody
	CustomError onIllegalMongoDatabaseNameException(HttpServletRequest webRequest, IllegalMongoDatabaseNameException ex) {
		return renderError(HttpStatus.NOT_FOUND, webRequest, databaseNotFoundMessage(ex.getDatabaseName()));
	}

	@ExceptionHandler(IllegalMongoDatabaseAccessException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ResponseBody
	CustomError IllegalMongoDatabaseAccessException(HttpServletRequest webRequest, IllegalMongoDatabaseAccessException ex) {
		return renderError(HttpStatus.NOT_FOUND, webRequest, databaseNotFoundMessage(ex.getDatabaseName()));
	}

	@ExceptionHandler(IllegalMongoCollectionNameException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ResponseBody
	CustomError onIllegalMongoCollectionNameException(HttpServletRequest webRequest, IllegalMongoCollectionNameException ex) {
		return renderError(HttpStatus.NOT_FOUND, webRequest, collectionNotFoundMessage(ex.getCollectionName()));
	}

	@ExceptionHandler(IllegalMongoCollectionAccessException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ResponseBody
	CustomError onIllegalMongoCollectionNameException(HttpServletRequest webRequest, IllegalMongoCollectionAccessException ex) {
		return renderError(HttpStatus.NOT_FOUND, webRequest, collectionNotFoundMessage(ex.getCollectionName()));
	}

	private static String collectionNotFoundMessage(String collectionName) {
		return "Collection '" + collectionName + "' does not exist";
	}

	private static String databaseNotFoundMessage(String collectionName) {
		return "Database '" + collectionName + "' does not exist";
	}

	private static CustomError renderError(HttpStatus status, HttpServletRequest webRequest, String message) {
		return new CustomError(status.value(), message, webRequest.getServletPath());
	}

	private static CustomError renderError(HttpStatus status, HttpServletRequest webRequest, Exception ex) {
		return renderError(status, webRequest, ex.getMessage());
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
