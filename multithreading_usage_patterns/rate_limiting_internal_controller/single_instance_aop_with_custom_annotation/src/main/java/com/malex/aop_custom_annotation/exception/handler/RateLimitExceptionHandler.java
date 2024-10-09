package com.malex.aop_custom_annotation.exception.handler;

import com.malex.aop_custom_annotation.exception.RateLimitException;
import com.malex.aop_custom_annotation.exception.model.ApiErrorMessage;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
/*
 * @Order defines the sort order for an annotated component.
 * The value is optional and represents an order value as defined in the Ordered interface.
 * Lower values have higher priority.
 * The default value is Ordered. LOWEST_PRECEDENCE, indicating the lowest priority
 * (losing to any other specified order value).
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
public class RateLimitExceptionHandler {

  @ExceptionHandler(RateLimitException.class)
  public ResponseEntity<ApiErrorMessage> handleInvalidFieldsInValidJson(
      final RateLimitException rateLimitException, final HttpServletRequest request) {
    final var apiErrorMessage = rateLimitException.toApiErrorMessage(request.getRequestURI());
    log.error(
        String.format("%s: %s", apiErrorMessage.getId(), rateLimitException.getMessage()),
        rateLimitException);
    return new ResponseEntity<>(apiErrorMessage, HttpStatus.TOO_MANY_REQUESTS);
  }
}
