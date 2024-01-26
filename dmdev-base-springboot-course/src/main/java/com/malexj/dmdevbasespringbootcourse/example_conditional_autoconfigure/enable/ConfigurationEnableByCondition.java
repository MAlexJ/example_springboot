package com.malexj.dmdevbasespringbootcourse.example_conditional_autoconfigure.enable;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("conditional_autoconfigure_profile")
@Component
@Conditional(ConditionIsEnabled.class)
public class ConfigurationEnableByCondition {

  private static final Logger LOG = LoggerFactory.getLogger(ConfigurationEnableByCondition.class);

  @PostConstruct
  public void init() {
    LOG.info(">>> init - {}", this.getClass().getName());
  }

  @PreDestroy
  public void destroy() {
    LOG.info(">>> destroy - {}", this.getClass().getName());
  }
}
