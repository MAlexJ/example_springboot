package com.malexj.handler;

public class WebSocketHandlerException extends RuntimeException {

  public WebSocketHandlerException(String errorMessage, Exception e) {
    super(errorMessage, e);
  }
}
