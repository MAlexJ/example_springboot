package com.malex.configurationproperties;

import com.malex.configurationproperties.configuration.AppConfiguration;
import com.malex.configurationproperties.props.RestaurantConfigurationProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@Slf4j
@RequiredArgsConstructor
@SpringBootApplication
public class ConfigurationPropertiesApplication {

  private final RestaurantConfigurationProperties properties;
  private final AppConfiguration.BeanRestaurantConfigurationProperties beanProperties;

  public static void main(String[] args) {
    SpringApplication.run(ConfigurationPropertiesApplication.class, args);
  }

  @EventListener(ApplicationReadyEvent.class)
  public void onReady() {
    log.info(properties.getName());
    log.info("name - {}", properties.getName());
    log.info("menu - {}", properties.getMenu());
    log.info("employees - {}", properties.getEmployees());
  }

  @EventListener(ApplicationReadyEvent.class)
  public void onReadyNew() {
    log.info(beanProperties.getName());
    log.info("Bean name - {}", beanProperties.getName());
    log.info("Bean menu - {}", beanProperties.getMenu());
    log.info("Bean employees - {}", beanProperties.getEmployees());
  }
}
