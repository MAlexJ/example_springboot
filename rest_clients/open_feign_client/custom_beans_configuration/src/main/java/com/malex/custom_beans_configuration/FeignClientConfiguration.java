package com.malex.custom_beans_configuration;

import feign.okhttp.OkHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignClientConfiguration {

  @Bean
  public OkHttpClient client() {
    return new OkHttpClient();
  }
}
