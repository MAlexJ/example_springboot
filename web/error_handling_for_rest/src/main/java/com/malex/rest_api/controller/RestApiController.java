package com.malex.rest_api.controller;

import com.malex.rest_api.exception.SubscriptionException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@RestController
public class RestApiController {

  /*
   * Controller-Level @ExceptionHandler
   *
   * link: https://www.baeldung.com/exception-handling-for-rest-with-spring#exceptionhandler
   */
  @ExceptionHandler({SubscriptionException.class})
  public ResponseEntity<Object> handleException(SubscriptionException ex, WebRequest request) {

    var errorMessage = ex.getMessage();
    log.error(errorMessage, ex);
    throw new ResponseStatusException(HttpStatus.NOT_FOUND, errorMessage);
  }
}
