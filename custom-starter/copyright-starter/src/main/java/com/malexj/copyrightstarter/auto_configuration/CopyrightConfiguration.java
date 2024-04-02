package com.malexj.copyrightstarter.auto_configuration;

import com.malexj.copyrightstarter.props.StarterCopyrightProperties;
import com.malexj.copyrightstarter.service.CopyrightService;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
@EnableConfigurationProperties(StarterCopyrightProperties.class)
public class CopyrightConfiguration {

  @Bean
  public CopyrightService copyrightService(StarterCopyrightProperties properties) {
    return new CopyrightService(properties);
  }
}
