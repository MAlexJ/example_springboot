package com.malex.webservice;

import java.time.Duration;
import org.springframework.boot.web.client.ClientHttpRequestFactories;
import org.springframework.boot.web.client.ClientHttpRequestFactorySettings;
import org.springframework.http.client.JdkClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class WebService {

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
  public WebService(RestClient.Builder builder) {
    var requestFactorySettings =
        ClientHttpRequestFactorySettings.DEFAULTS
            .withConnectTimeout(CONNECT_TIMEOUT)
            .withReadTimeout(READ_TIMEOUT);

    var requestFactory =
        ClientHttpRequestFactories.get(JdkClientHttpRequestFactory.class, requestFactorySettings);

    this.restClient = builder.baseUrl(BASE_URL).requestFactory(requestFactory).build();
  }

  public String findInfo() {
    return restClient.get().uri("/v1/api/internal").retrieve().body(String.class);
  }
}
