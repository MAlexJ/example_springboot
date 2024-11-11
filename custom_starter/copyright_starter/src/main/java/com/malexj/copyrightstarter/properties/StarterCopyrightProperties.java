package com.malexj.copyrightstarter.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.bind.DefaultValue;

@ConfigurationProperties(prefix = "starter-copyright")
@ConfigurationPropertiesScan("default-application.yml")
public record StarterCopyrightProperties(@DefaultValue("author") String author) {}
