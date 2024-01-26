package com.malexj.dmdevbasespringbootcourse.config;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfiguration {

  private static final Logger LOG = LoggerFactory.getLogger(ApplicationConfiguration.class);

  @PostConstruct
  public void init() {
    LOG.info("init - {}", this.getClass().getName());
  }

  @PreDestroy
  public void destroy() {
    LOG.info("destroy - {}", this.getClass().getName());
  }
}
