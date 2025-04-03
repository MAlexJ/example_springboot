package com.malex.interceptor.webservice.post.interceptor;

import java.io.IOException;
import java.time.Instant;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Service;

@Service
public class SimpleRetryHttpInterceptor implements ClientHttpRequestInterceptor {

  private static final Logger LOG = Logger.getLogger(SimpleRetryHttpInterceptor.class.getName());

  private static final int ATTEMPTS = 3;

  @Override
  public ClientHttpResponse intercept(
      HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {

    for (int i = 0; i < ATTEMPTS; i++) {

      var clientHttpResponse = execution.execute(request, body);

      if (!clientHttpResponse.getStatusCode().isSameCodeAs(HttpStatus.TOO_MANY_REQUESTS)) {
        if (LOG.isLoggable(Level.INFO)) LOG.info("Successful at the %d attempts".formatted(i + 1));
        return clientHttpResponse;
      }

      try {
        TimeUnit.MILLISECONDS.sleep(700);
      } catch (InterruptedException e) {
        LOG.log(Level.WARNING, "Interrupted!", e);
        /* Clean up whatever needs to be handled before interrupting  */
        Thread.currentThread().interrupt();
      }
      if (LOG.isLoggable(Level.INFO)) LOG.info("%d attempts: %s".formatted(i + 1, Instant.now()));
    }

    LOG.severe("Retry exhausted!");
    throw new IllegalStateException("Exceed number of retries");
  }
}
