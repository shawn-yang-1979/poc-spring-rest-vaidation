package com.shawnyang.poc.spring.rest;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.AbstractHandlerExceptionResolver;
import org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver;

@Order(Ordered.HIGHEST_PRECEDENCE)
@Component
public class CustomHandlerExceptionResolver extends AbstractHandlerExceptionResolver {

	@Override
	protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception ex) {
		// TODO Auto-generated method stub
		return null;
	}


}
