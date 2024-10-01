package com.malex.configurationproperties.props;

import java.util.List;
import java.util.Map;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "app.restaurant")
public class RestaurantConfigurationProperties {

  private String name;
  private List<String> menu;
  private Map<String, EmployeeInfo> employees;
}
