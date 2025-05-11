package com.malex.leaky_bucket_with_bucket4j_and_redis.configuration;

import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedisConfig {

  @Value("${redis.host}")
  private String host;

  @Value("${redis.port}")
  private int port;

  @Value("${redis.username}")
  private String username;

  @Value("${redis.password}")
  private String password;

  @Bean
  public RedisClient redisClient() {
    return RedisClient.create(
        RedisURI.builder()
            .withHost(host)
            .withPort(port)
            .withAuthentication(username, password)
            .build());
  }
}
