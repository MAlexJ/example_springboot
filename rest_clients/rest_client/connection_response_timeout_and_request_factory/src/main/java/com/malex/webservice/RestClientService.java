package com.malex.webservice;

import java.time.Duration;
import org.springframework.boot.http.client.ClientHttpRequestFactoryBuilder;
import org.springframework.boot.http.client.ClientHttpRequestFactorySettings;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class RestClientService {

  /** Connection Timeout (connectTimeout) */
  private static final Duration CONNECT_TIMEOUT = Duration.ofSeconds(1L);

  /** Response Timeout (readTimeout) */
  private static final Duration READ_TIMEOUT = Duration.ofSeconds(5L);

  private static final String BASE_URL = "http://localhost:8888";

  private final RestClient restClient;

  /**
   * Spring simplified the configuration of underlying components by
   * `ClientHttpRequestFactorySettings` and `ClientHttpRequestFactories`
   */
  public RestClientService(RestClient.Builder builder) {

    /*
     * Settings that can be applied when creating a ClientHttpRequestFactory.
     * Since: 3.4.0
     */
    var clientHttpRequestFactorySettings =
        ClientHttpRequestFactorySettings.defaults()
            .withReadTimeout(READ_TIMEOUT)
            .withConnectTimeout(CONNECT_TIMEOUT);

    /*
     * Builders for Apache HTTP Components, Jetty, Reactor, JDK and simple client can be obtained using
     * the factory methods on this interface.
     * Since: 3.4.0
     */
    var requestFactory =
        ClientHttpRequestFactoryBuilder.jdk().build(clientHttpRequestFactorySettings);

    this.restClient = builder.baseUrl(BASE_URL).requestFactory(requestFactory).build();
  }

  public String findInfo() {
    return restClient.get().uri("/v1/api/internal").retrieve().body(String.class);
  }
}
