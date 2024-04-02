package com.malexj.copyrightstarter.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "starter-copyright")
public class StarterCopyrightProperties {

  private String author;
}
