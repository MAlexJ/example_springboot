package com.malex.handler_exception_resolver_implementation.exception;

import java.io.IOException;

public class ApplicationException extends RuntimeException {

  public ApplicationException(IOException ex) {
    super(ex);
  }
}
