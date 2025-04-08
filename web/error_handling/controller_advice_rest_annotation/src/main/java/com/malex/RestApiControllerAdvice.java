package com.malex;

import java.net.URI;
import org.springframework.http.HttpStatus;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice(basePackageClasses = RestApiController.class)
public class RestApiControllerAdvice {

  @ExceptionHandler(value = {RuntimeException.class})
  public ErrorResponse mapInvalidCallException(RuntimeException ex, WebRequest request) {
    return ErrorResponse.builder(ex, HttpStatus.BAD_REQUEST, ex.getMessage())
        .title("Validate Error")
        .type(URI.create(request.getContextPath()))
        .property("customCode", "VALIDATE_ERR")
        .build();
  }

  @ExceptionHandler(value = {IllegalArgumentException.class})
  public ErrorResponse mapNotFoundException(IllegalArgumentException ex, WebRequest request) {
    return ErrorResponse.builder(ex, HttpStatus.BAD_REQUEST, ex.getMessage())
        .title("Business Error")
        .type(URI.create(request.getContextPath()))
        .property("customCode", "BUSINESS_ERR")
        .build();
  }
}
