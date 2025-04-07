package com.malex.handler;

import java.net.URI;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.malex.exception.InvalidInputException;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(InvalidInputException.class)
  public ProblemDetail handleInvalidInputException(InvalidInputException e, WebRequest request) {

    ProblemDetail problemDetail =
        ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getMessage());

    problemDetail.setInstance(URI.create("discount"));

    return problemDetail;
  }
}
