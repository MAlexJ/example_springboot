package com.malex.error_handling.exception.handler;

import com.malex.error_handling.exception.BadRequestException;
import com.malex.error_handling.exception.NotFoundException;
import com.malex.error_handling.exception.UnauthorizedException;
import com.malex.error_handling.exception.model.ApiErrorMessage;
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
public class RestApiExceptionHandler {

  @ExceptionHandler(UnauthorizedException.class)
  public ResponseEntity<ApiErrorMessage> handleUnauthorizedException(
      UnauthorizedException exception, HttpServletRequest request) {
    var uri = request.getRequestURI();
    var apiError = exception.toApiErrorMessage(uri);
    return new ResponseEntity<>(apiError, HttpStatus.UNAUTHORIZED);
  }

  @ExceptionHandler(BadRequestException.class)
  public ResponseEntity<ApiErrorMessage> handleBadRequestException(
      BadRequestException exception, HttpServletRequest request) {
    var uri = request.getRequestURI();
    var apiErrorMessage = exception.toApiErrorMessage(uri);
    return new ResponseEntity<>(apiErrorMessage, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(NotFoundException.class)
  public ResponseEntity<ApiErrorMessage> handleNotFoundException(
      NotFoundException ex, HttpServletRequest req) {
    var uri = req.getRequestURI();
    var apiError = ex.toApiErrorMessage(uri);
    return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
  }
}
