package com.malex.redis_data_store_for_cache.configuration;

import com.malex.redis_data_store_for_cache.model.User;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfiguration {

  @Bean("in_memory_user_db")
  public List<User> inMemoryDb() {
    return new CopyOnWriteArrayList<>();
  }

}
