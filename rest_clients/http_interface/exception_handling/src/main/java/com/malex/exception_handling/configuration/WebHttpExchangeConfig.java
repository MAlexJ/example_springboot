package com.malex.exception_handling.configuration;

import com.malex.exception_handling.exception.ServerNotAvailableException;
import com.malex.exception_handling.webservice.WebHttpExchangeInterface;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;
import reactor.core.publisher.Mono;

@Slf4j
@Configuration
public class WebHttpExchangeConfig {

  @Value("${webservice.client.url}")
  private String httpBinUrl;

  @Bean
  public WebClient httpBinWebClient() {
    return WebClient.builder()
        .baseUrl(httpBinUrl)
        .defaultStatusHandler(
            httpStatusCode -> {
              boolean httpStatusCode2xxSuccessful = httpStatusCode.is2xxSuccessful();
              if (httpStatusCode2xxSuccessful) {
                log.info("Http status code - {}", httpStatusCode);
              }
              boolean error = httpStatusCode.isError();
              boolean httpStatusCode4xxClientError = httpStatusCode.is4xxClientError();

              boolean httpStatusCode5xxServerError = httpStatusCode.is5xxServerError();
              if (error) {
                log.error(
                    "Http error status code - {} , is 4xx - {} or is 5xx - {}",
                    httpStatusCode,
                    httpStatusCode4xxClientError,
                    httpStatusCode5xxServerError);
              }
              return httpStatusCode5xxServerError;
            },
            clientResponse -> Mono.just(new ServerNotAvailableException("Custom exception")))
        .defaultStatusHandler(
            HttpStatusCode::is4xxClientError,
            clientResponse ->
                Mono.error(
                    new ResponseStatusException(
                        clientResponse.statusCode(), clientResponse.toString())))
        .build();
  }

  @Bean
  public WebHttpExchangeInterface webHttpExchangeInterface(WebClient httpBinWebClient) {
    var webClientAdapter = WebClientAdapter.create(httpBinWebClient);
    var httpServiceProxyFactory = HttpServiceProxyFactory.builderFor(webClientAdapter).build();
    return httpServiceProxyFactory.createClient(WebHttpExchangeInterface.class);
  }
}
