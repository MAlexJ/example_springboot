package com.malex.webservice.configuration;

import feign.Response;
import feign.RetryableException;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class FeignErrorDecoder implements ErrorDecoder {

  /*
   * HTTP headers are used to pass additional information with HTTP request or response.
   * HTTP Retry-After header is an HTTP response header which indicates how long to wait before making another request.
   * Depending on different status codes, there are different use cases of the Retry-After response header.
   *
   * 1. Status code 503: 503 is used to indicate service unavailable.
   * Retry-After is used with 503 which tells the user how long the service is expected to be unavailable.
   * It is used to deal with scheduled downtime.
   *
   * 2. Status code 301: 301 is used to indicate that resource is moved permanently.
   * Retry-After is used with 301 which tells the user about the minimum
   * for which user should wait before issuing the redirect request.
   *
   * 3. Status code 429: 429 id is used to indicate too many request.
   * Retry-After is used with 429 to tells the user how long to wait before making another request.
   */
  private static final long RETRY_AFTER = 10000;

  private final ErrorDecoder defaultErrorDecoder = new Default();

  @Override
  public Exception decode(String methodKey, Response response) {
    var exception = defaultErrorDecoder.decode(methodKey, response);
    var status = response.status();
    var request = response.request();
    var httpMethod = request.httpMethod();
    var err = exception.getMessage();
    log.error("******************** START OF RETRY");
    log.error("**** Feign status -{}", status);
    log.error("**** Feign methodKey - {}", methodKey);
    return new RetryableException(status, err, httpMethod, exception, RETRY_AFTER, request);
  }
}
