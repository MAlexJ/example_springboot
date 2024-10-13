package com.malexj.controller;

import com.malexj.exception.WebApplicationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Additional information: <br>
 * link: https://medium.com/@aedemirsen/spring-boot-global-exception-handler-842d7143cf2a <br>
 * link: https://www.baeldung.com/exception-handling-for-rest-with-spring
 */
@ControllerAdvice
public class ControllerExceptionHandler {

  @ExceptionHandler({WebApplicationException.class})
  public ResponseEntity<Object> handleStudentNotFoundException(WebApplicationException exception) {
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exception.getMessage());
  }

  @ExceptionHandler({RuntimeException.class})
  public ResponseEntity<Object> handleRuntimeException(RuntimeException exception) {
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exception.getMessage());
  }
}
