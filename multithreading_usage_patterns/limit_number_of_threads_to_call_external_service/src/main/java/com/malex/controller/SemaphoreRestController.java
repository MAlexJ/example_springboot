package com.malex.controller;

import com.malex.exception.CustomAppException;
import com.malex.service.semaphore_service.SemaphoreService;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@RestController
@RequiredArgsConstructor
public class SemaphoreRestController {

  public final SemaphoreService semaphoreService;

  @GetMapping("/v1/rate-limit/semaphore/{value}")
  public ResponseEntity<String> rateLimit(@PathVariable String value) {
    return Optional.ofNullable(value)
        /*
         * Check whether the given String contains actual text.
         * More specifically, this method returns true if the String is not null, its length is greater than 0,
         * and it contains at least one non-whitespace character.
         */
        .filter(StringUtils::hasText)
        .map(valueStr -> ResponseEntity.ok(semaphoreService.rateLimiting(value)))
        .orElse(ResponseEntity.badRequest().build());
  }

  /*
   * Controller-Level @ExceptionHandler
   *
   * link: https://www.baeldung.com/exception-handling-for-rest-with-spring#exceptionhandler
   */
  @ExceptionHandler({CustomAppException.class})
  public ResponseEntity<Object> handleException(CustomAppException ex) {
    var errorMessage = ex.getMessage();
    log.error(errorMessage, ex);
    // HttpStatus.TOO_MANY_REQUESTS = 429 status code
    throw new ResponseStatusException(HttpStatus.TOO_MANY_REQUESTS, errorMessage);
  }
}
