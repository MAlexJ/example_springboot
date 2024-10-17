package com.malex.error_handling.config;

import com.malex.error_handling.exception.BadRequestException;
import com.malex.error_handling.exception.NotFoundException;
import com.malex.error_handling.exception.UnauthorizedException;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CustomErrorDecoder implements ErrorDecoder {

  private static final String ERROR_TEMPLATE =
      "When calling - '%s' method, an error - '%s' occurred";

  @Override
  public Exception decode(String methodKey, Response response) {
    var errorMessage = String.format(ERROR_TEMPLATE, methodKey, response.reason());
    return switch (response.status()) {
      case 400 -> new BadRequestException(errorMessage);
      case 401 -> new UnauthorizedException(errorMessage);
      case 404 -> new NotFoundException(errorMessage);
      default -> new Exception(errorMessage);
    };
  }
}
