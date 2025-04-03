package com.malexj.request_factory.webservice;

import java.time.Duration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.http.client.ClientHttpRequestFactoryBuilder;
import org.springframework.boot.http.client.ClientHttpRequestFactorySettings;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class RestClientService {

  @Value("${webservice.internal.path.posts.findAll}")
  private String findAllPostsPath;

  private final RestClient restClient;

  public RestClientService(
      @Value("${webservice.internal.url}") String baseUrl,
      @Value("${webservice.connectTimeout}") Long connectTimeout,
      @Value("${webservice.readTimeout}") Long readTimeout,
      RestClient.Builder builder) {
    this.restClient =
        builder
            .baseUrl(baseUrl)
            .requestFactory(requestFactory(connectTimeout, readTimeout))
            .build();
  }

  private ClientHttpRequestFactory requestFactory(Long connectTimeout, Long readTimeout) {
    var settings =
        ClientHttpRequestFactorySettings.defaults()
            .withConnectTimeout(Duration.ofSeconds(connectTimeout))
            .withReadTimeout(Duration.ofSeconds(readTimeout));

    return ClientHttpRequestFactoryBuilder.jdk().build(settings);
  }

  public String getInfo() {
    return restClient
        .get()
        .uri(findAllPostsPath)
        .retrieve()
        .body(new ParameterizedTypeReference<>() {}); // todo: server respond 429, what the status
    // code of this server?
  }
}
