package com.malex.config;

import com.malex.model.entity.UserEntity;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfiguration {

  @Bean
  public Map<Integer, UserEntity> inMemoryDatabase() {
    return new ConcurrentHashMap<>();
  }
}
