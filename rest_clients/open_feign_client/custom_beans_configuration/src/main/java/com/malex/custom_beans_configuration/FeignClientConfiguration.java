package com.malex.custom_beans_configuration;

import feign.okhttp.OkHttpClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;

@Slf4j
public class FeignClientConfiguration {

  @Bean
  public OkHttpClient client() {
    log.info("Feign client configured with OkHttpClient");
    return new OkHttpClient();
  }
}
