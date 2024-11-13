package com.malex.interceptor.post.webclient.interceptor;

import java.io.IOException;
import java.time.Instant;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Service;

@Service
public class SimpleReplayHttpInterceptor implements ClientHttpRequestInterceptor {

  private static final Logger LOG = Logger.getLogger(SimpleReplayHttpInterceptor.class.getName());

  private static final int ATTEMPTS = 3;

  @Override
  public ClientHttpResponse intercept(
      HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {

    for (int i = 0; i < ATTEMPTS; i++) {

      var clientHttpResponse = execution.execute(request, body);

      if (!clientHttpResponse.getStatusCode().isSameCodeAs(HttpStatus.TOO_MANY_REQUESTS)) {
        LOG.info("Successful at the %d attempts".formatted(i + 1));
        return clientHttpResponse;
      }

      try {
        TimeUnit.MILLISECONDS.sleep(700);
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      }

      LOG.info("%d attempts: %s".formatted(i + 1, Instant.now()));
    }
    LOG.severe("Retry exhausted!");
    throw new IllegalStateException("Exceed number of retries");
  }
}
