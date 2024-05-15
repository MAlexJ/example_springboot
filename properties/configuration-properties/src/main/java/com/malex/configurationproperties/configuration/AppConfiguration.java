package com.malex.configurationproperties.configuration;

import com.malex.configurationproperties.props.EmployeeInfo;
import java.util.List;
import java.util.Map;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfiguration {

  @Bean
  public BeanRestaurantConfigurationProperties beanRestaurantConfigurationProperties() {
    return new BeanRestaurantConfigurationProperties();
  }

  @Data
  @ConfigurationProperties(prefix = "bean.restaurant")
  public static class BeanRestaurantConfigurationProperties {
    private String name;
    private List<String> menu;
    private Map<String, EmployeeInfo> employees;
  }
}
