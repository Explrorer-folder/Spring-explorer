package com.barabanov.spring.http.handler;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@RestControllerAdvice
@ControllerAdvice(basePackages = "com.barabanov.spring.http.rest")
public class RestControllerExceptionHandler extends ResponseEntityExceptionHandler {
}
