package com.malex.multipledatabasesspringdatajpa.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "spring.datasource.mysql")
public class MysqlDatasourceConfigurationProperties {
  private String url;
  private String username;
  private String password;
  private String driverClassName;
}
