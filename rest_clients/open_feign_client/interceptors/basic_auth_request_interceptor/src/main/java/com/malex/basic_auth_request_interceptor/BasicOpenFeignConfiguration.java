package com.malex.basic_auth_request_interceptor;

import feign.auth.BasicAuthRequestInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BasicOpenFeignConfiguration {

  @Value("${service.client.user}")
  private String username;

  @Value("${service.client.password}")
  private String password;

  /*
   * An interceptor that adds the request header needed to use HTTP basic authentication
   */
  @Bean
  public BasicAuthRequestInterceptor basicAuthRequestInterceptor() {
    return new BasicAuthRequestInterceptor(username, password);
  }
}
