package com.malexj.copyrightstarter.props;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@Getter
@Setter
@ConfigurationProperties(prefix = "starter-copyright")
@ConfigurationPropertiesScan("default-application.yml")
public class StarterCopyrightProperties {

  private String author;
}
