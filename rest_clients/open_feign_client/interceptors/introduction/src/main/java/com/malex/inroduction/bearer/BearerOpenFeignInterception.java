package com.malex.inroduction.bearer;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import java.util.Base64;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

@Component
public class BearerOpenFeignInterception implements RequestInterceptor {

  /*
   * Bearer access authentication
   */
  private static final String BEARER_TOKEN_PREFIX = "Bearer %s";
  private static final String TOKEN_PREFIX = "%s:%s";
  private static final String PATH_TEMPLATE = "/%s";

  @Value("${service.client.user}")
  private String username;

  @Value("${service.client.password}")
  private String password;

  @Value("${service.client.path.bearer}")
  private String bearerPath;

  @Override
  public void apply(RequestTemplate requestTemplate) {
    Optional.of(requestTemplate.url())
        .filter(url -> url.startsWith(String.format(PATH_TEMPLATE, bearerPath)))
        .map(url -> addBearerToken(requestTemplate));
  }

  private RequestTemplate addBearerToken(RequestTemplate requestTemplate) {
    var authorizationToken = buildToken();
    requestTemplate.header(
        HttpHeaders.AUTHORIZATION, String.format(BEARER_TOKEN_PREFIX, authorizationToken));
    requestTemplate.header(HttpHeaders.ACCEPT, APPLICATION_JSON_VALUE);
    return requestTemplate;
  }

  private String buildToken() {
    var encoder = Base64.getEncoder();
    byte[] token = String.format(TOKEN_PREFIX, username, password).getBytes();
    return encoder.encodeToString(token);
  }
}
