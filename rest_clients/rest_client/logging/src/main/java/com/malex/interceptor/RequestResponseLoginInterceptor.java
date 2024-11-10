package com.malex.interceptor;

import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class RequestResponseLoginInterceptor implements ClientHttpRequestInterceptor {

  @Override
  public ClientHttpResponse intercept(
      HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {

    var method = request.getMethod();
    var uri = request.getURI();
    var headers = request.getHeaders();
    // request body !!!???
    log.info("Request URI: - {}: - {}", method, uri);
    log.info("Request headers: - {}", headers);

    return execution.execute(request, body);
  }
}
