package com.malexj.exception;

public class WebApplicationException extends RuntimeException {

  public WebApplicationException(String message, Throwable cause) {
    super(message, cause);
  }
}
