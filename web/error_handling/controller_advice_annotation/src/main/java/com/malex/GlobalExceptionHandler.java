package com.malex;

import java.net.URI;
import org.springframework.http.HttpStatus;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

  /** Provides handling for exceptions throughout this service. */
  @ExceptionHandler({IllegalArgumentException.class, RuntimeException.class})
  public final ErrorResponse handleException(Exception ex, WebRequest request) {

    if (ex instanceof IllegalArgumentException) {
      return ErrorResponse.builder(ex, HttpStatus.NOT_FOUND, ex.getMessage())
          .title("IllegalArgumentException")
          .type(URI.create(request.getContextPath()))
          .property("props", "NOT_FOUND")
          .build();
    }

    if (ex instanceof RuntimeException) {
      return ErrorResponse.builder(ex, HttpStatus.BAD_REQUEST, ex.getMessage())
          .title("RuntimeException")
          .type(URI.create(request.getContextPath()))
          .property("props", "BAD_REQUEST")
          .build();
    }

    HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
    return ErrorResponse.builder(ex, status, ex.getMessage())
        .title("Server Error")
        .type(URI.create(request.getContextPath()))
        .property("props", "INTERNAL_SERVER_ERROR")
        .build();
  }
}
