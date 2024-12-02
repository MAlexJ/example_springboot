package com.malex.redis_data_store_for_cache.database.config;

import com.malex.redis_data_store_for_cache.database.entity.UserEntity;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InMemoryDatabaseConfiguration {

  @Bean("inMemoryUserDatabase")
  public Map<Long, UserEntity> inMemoryDatabase() {
    return new ConcurrentHashMap<>();
  }

  @Bean("inMemoryPrimaryKeyGenerator")
  public AtomicLong generatePrimaryKey() {
    return new AtomicLong(0);
  }
}
