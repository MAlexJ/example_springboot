package com.malex.error_handling.status;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.server.ResponseStatusException;

@Service
public class StatusWebClient {

  @Value("${webclient.client.path.status}")
  private String statusPath;

  private final RestClient restClient;

  public StatusWebClient(
      @Value("${webclient.client.url}") String baseUrl, RestClient.Builder builder) {
    this.restClient = builder.baseUrl(baseUrl).build();
  }

  /*
  * Test error handling
  *
  * ResponseStatusException (Spring 5 + )
     Spring 5 introduced the ResponseStatusException class.
     We can create an instance of it providing an HttpStatus and optionally a reason and a cause:
     For more details and further examples, see - https://www.baeldung.com/spring-response-status-exception
  */
  public String getStatus(Integer status) {
    return restClient
        .get()
        .uri(uriBuilder -> uriBuilder.path(statusPath).build(status))
        .retrieve()
        .onStatus(
            HttpStatusCode::is4xxClientError,
            (request, response) -> {
              throw new ResponseStatusException(
                  HttpStatus.BAD_REQUEST, "API error %s".formatted(response.getStatusText()));
            })
        .onStatus(
            HttpStatusCode::is5xxServerError,
            (request, response) -> {
              throw new ResponseStatusException(
                  HttpStatus.INTERNAL_SERVER_ERROR,
                  "API error %s".formatted(response.getStatusText()));
            })
        .body(String.class);
  }
}
