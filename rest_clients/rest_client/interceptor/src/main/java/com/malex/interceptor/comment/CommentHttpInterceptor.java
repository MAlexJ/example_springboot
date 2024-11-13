package com.malex.interceptor.comment;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.logging.Logger;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Service;

@Service
public class CommentHttpInterceptor implements ClientHttpRequestInterceptor {

  private static final Logger LOG = Logger.getLogger(CommentHttpInterceptor.class.getName());

  @Override
  public ClientHttpResponse intercept(
      HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {

    LOG.info(" >>> Request method: " + request.getMethod());
    LOG.info(" >>> Request body: " + new String(body, StandardCharsets.UTF_8));
    ClientHttpResponse clientHttpResponse = execution.execute(request, body);
    LOG.info(" >>> Response status:" + clientHttpResponse.getStatusText());

    return clientHttpResponse;
  }
}
