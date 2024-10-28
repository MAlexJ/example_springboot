package com.malex.annotation_configuration.webservice.configuration;

import feign.Retryer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class OpenFeignRetryer {

  /*
   * Retry 3 times with 100ms initial interval and 1000ms max interval
   */
  @Bean
  public Retryer retryer() {
    return new Retryer.Default(100, 1000, 3);
  }
}
