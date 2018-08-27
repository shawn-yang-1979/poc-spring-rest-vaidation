package com.shawnyang.poc.spring.rest;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class PocExceptionHandler {

	private static final Logger logger = LoggerFactory.getLogger(PocExceptionHandler.class);

	// ServletRequestBindingException
	// HttpMessageNotReadableException
	// MissingServletRequestPartException

	@ExceptionHandler
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public List<Error> handle(MethodArgumentNotValidException ex) {
		logger.error(ex.getMessage(), ex);
		BindingResult br = ex.getBindingResult();
		return convertBindingResult(br);
	}

	@ExceptionHandler
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public List<Error> handle(BindException ex) {
		logger.error(ex.getMessage(), ex);
		return convertBindingResult(ex);
	}

	@ExceptionHandler
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public List<Error> handle(TypeMismatchException ex) {
		logger.error(ex.getMessage(), ex);
		List<Error> errors = new LinkedList<>();
		Error error = new Error();
		error.setCode(ex.getErrorCode());
		error.setArgument(ex.getPropertyName());
		error.setInvalidValue(ex.getValue());
		errors.add(error);
		return errors;
	}

	@ExceptionHandler
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public List<Error> handle(MissingServletRequestParameterException ex) {
		logger.error(ex.getMessage(), ex);
		List<Error> errors = new LinkedList<>();
		Error error = new Error();
		error.setCode("MissingParameter");
		error.setArgument(ex.getParameterName());
		errors.add(error);
		return errors;
	}

	private List<Error> convertBindingResult(BindingResult br) {
		List<Error> errors = new ArrayList<>();
		for (FieldError fieldError : br.getFieldErrors()) {
			Error error = new Error();
			error.setCode(fieldError.getCode());
			error.setArgument(fieldError.getObjectName() + "." + fieldError.getField());
			error.setInvalidValue(fieldError.getRejectedValue());
			errors.add(error);
		}
		return errors;
	}

	@ExceptionHandler
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public List<Error> handle(ConstraintViolationException ex) {
		logger.error(ex.getMessage(), ex);

		List<Error> errors = new LinkedList<>();
		for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
			Error error = new Error();
			error.setCode(violation.getConstraintDescriptor().getAnnotation().annotationType().getSimpleName());
			error.setArgument(violation.getPropertyPath().toString());
			error.setInvalidValue(violation.getInvalidValue());
			
			errors.add(error);
			
			logger.error(violation.getRootBeanClass().getSimpleName());
			logger.error(violation.getMessageTemplate());
			logger.error(violation.getLeafBean() + "");
		}

		return errors;
	}

	public static class Error {

		private String code;
		private String method;
		private String argument;
		private Object invalidValue;

		public String getCode() {
			return code;
		}

		public void setCode(String code) {
			this.code = code;
		}

		public String getMethod() {
			return method;
		}

		public void setMethod(String method) {
			this.method = method;
		}

		public String getArgument() {
			return argument;
		}

		public void setArgument(String argument) {
			this.argument = argument;
		}

		public Object getInvalidValue() {
			return invalidValue;
		}

		public void setInvalidValue(Object invalidValue) {
			this.invalidValue = invalidValue;
		}

	}
}
