package com.malex.http_exchange;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class HttpClientProxyConfig {

  @Value("${webservice.client.url}")
  private String jsonPlaceholderURL;

  @Bean
  public WebClient jsonPlaceholderWebClient() {
    return WebClient.builder().baseUrl(jsonPlaceholderURL).build();
  }

  @Bean
  public PostExchangeInterface postsClient(WebClient jsonplaceholderWebClient) {
    var webClientAdapter = WebClientAdapter.create(jsonplaceholderWebClient);
    var httpServiceProxyFactory = HttpServiceProxyFactory.builderFor(webClientAdapter).build();
    return httpServiceProxyFactory.createClient(PostExchangeInterface.class);
  }
}
