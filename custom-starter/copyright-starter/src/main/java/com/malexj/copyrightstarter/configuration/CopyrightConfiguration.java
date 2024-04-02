package com.malexj.copyrightstarter.configuration;

import com.malexj.copyrightstarter.service.CopyrightService;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(StarterCopyrightProperties.class)
public class CopyrightConfiguration {

  @Bean
  public CopyrightService copyrightService(StarterCopyrightProperties properties) {
    return new CopyrightService(properties);
  }
}
