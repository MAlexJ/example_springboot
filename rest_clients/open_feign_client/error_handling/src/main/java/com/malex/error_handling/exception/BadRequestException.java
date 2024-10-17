package com.malex.error_handling.exception;

import com.malex.error_handling.exception.model.ApiErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class BadRequestException extends RuntimeException {

  private final HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

  public BadRequestException(String message) {
    super(message);
  }

  public ApiErrorMessage toApiErrorMessage(String path) {
    return new ApiErrorMessage(httpStatus.value(), httpStatus.name(), this.getMessage(), path);
  }
}
