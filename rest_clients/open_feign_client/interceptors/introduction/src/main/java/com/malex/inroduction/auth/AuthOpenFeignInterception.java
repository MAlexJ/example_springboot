package com.malex.inroduction.auth;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import java.util.Base64;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

@Component
public class AuthOpenFeignInterception implements RequestInterceptor {

  /*
   * Basic access authentication
   *
   * In basic HTTP authentication,
   * a request contains a header field in the form of Authorization: Basic <credentials>
   */
  private static final String BASIC_TOKEN_PREFIX = "Basic %s";

  /*
   * Basic access authentication
   *
   * <credentials> is the Base64 encoding of ID and password joined by a single colon ':'
   */
  private static final String TOKEN_PREFIX = "%s:%s";

  private static final String PATH_TEMPLATE = "/%s";

  @Value("${service.client.user}")
  private String username;

  @Value("${service.client.password}")
  private String password;

  @Value("${service.client.path.auth}")
  private String authPath;

  /*
   * Basic access authentication
   *
   * In the context of an HTTP transaction, basic access authentication is a method for an HTTP user agent
   *  (e.g. a web browser) to provide a username and password when making a request.
   *
   * In basic HTTP authentication, a request contains a header field in the form of Authorization: Basic <credentials>,
   * where <credentials> is the Base64 encoding of ID and password joined by a single colon :.
   *
   * link: https://en.wikipedia.org/wiki/Basic_access_authentication
   */
  @Override
  public void apply(RequestTemplate requestTemplate) {
    Optional.of(requestTemplate.url())
        .filter(url -> url.startsWith(String.format(PATH_TEMPLATE, authPath)))
        .map(
            url -> {
              var token = String.format(BASIC_TOKEN_PREFIX, buildToken());
              requestTemplate.header(HttpHeaders.AUTHORIZATION, token);
              requestTemplate.header(HttpHeaders.ACCEPT, APPLICATION_JSON_VALUE);
              return requestTemplate;
            });
  }

  private String buildToken() {
    var encoder = Base64.getEncoder();
    byte[] token = String.format(TOKEN_PREFIX, username, password).getBytes();
    return encoder.encodeToString(token);
  }
}
