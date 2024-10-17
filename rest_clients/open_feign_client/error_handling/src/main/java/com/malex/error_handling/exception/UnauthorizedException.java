package com.malex.error_handling.exception;

import com.malex.error_handling.exception.model.ApiErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/*
 * Spring ResponseStatusException
 *
 * Spring 5 introduced the ResponseStatusException class.
 * We can create an instance of it providing an HttpStatus and optionally a reason and a cause:
 *
 * A REST-ful application can communicate the success or failure of an HTTP request
 *  by returning the right status code in the response to the client.
 * Simply put, an appropriate status code can help the client to identify problems that might have occurred
 * while the application was dealing with the request.
 *
 * link: https://www.baeldung.com/spring-response-status-exception
 */
@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class UnauthorizedException extends RuntimeException {

  private final HttpStatus httpStatus = HttpStatus.UNAUTHORIZED;

  public UnauthorizedException(String message) {
    super(message);
  }

  public ApiErrorMessage toApiErrorMessage(String path) {
    return new ApiErrorMessage(httpStatus.value(), httpStatus.name(), this.getMessage(), path);
  }
}
