package com.malex.interceptor.webservice.comment.interceptor;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.logging.Logger;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Service;

@Service
public class SecondInterceptor implements ClientHttpRequestInterceptor {

  private static final Logger LOG = Logger.getLogger(SecondInterceptor.class.getName());

  @Override
  public ClientHttpResponse intercept(
      HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {

    LOG.info("    > Second >>> Request method: " + request.getMethod());

    if (body.length != 0) {
      LOG.info("    > Second >>> Request body: " + new String(body, StandardCharsets.UTF_8));
    }

    ClientHttpResponse clientHttpResponse = execution.execute(request, body);

    LOG.info("    > Second >>> Response status:" + clientHttpResponse.getStatusText());

    return clientHttpResponse;
  }
}
