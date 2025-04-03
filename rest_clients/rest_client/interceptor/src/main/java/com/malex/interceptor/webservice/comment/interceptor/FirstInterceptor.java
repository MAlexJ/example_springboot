package com.malex.interceptor.webservice.comment.interceptor;

import java.io.IOException;
import java.util.logging.Logger;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

@Component
public class FirstInterceptor implements ClientHttpRequestInterceptor {

  private static final Logger LOG = Logger.getLogger(FirstInterceptor.class.getName());

  @Override
  public ClientHttpResponse intercept(
      HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {

    LOG.info("  > First > Request method: " + request.getMethod());
    LOG.info("  > First > Request uri: " + request.getURI());
    LOG.info("  > First > Request headers: " + request.getHeaders());

    // add custom header
    request.getHeaders().add("X-First-Header", "first-value");

    ClientHttpResponse clientHttpResponse = execution.execute(request, body);

    LOG.info("  > First > Response status:" + clientHttpResponse.getStatusText());
    return clientHttpResponse;
  }
}
