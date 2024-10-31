package com.malex.exception_handling.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.SERVICE_UNAVAILABLE, reason = "Service Not Available")
public class ServerNotAvailableException extends RuntimeException {

  public ServerNotAvailableException(String message) {
    super(message);
  }
}
