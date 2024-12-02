package com.malex.redis_data_store_for_cache.cache.config;

import java.time.Duration;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.cache.RedisCacheManagerBuilderCustomizer;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;

/*
 * Spring Boot Cache with Redis
 *
 * link:
 */
@Configuration
@RequiredArgsConstructor
public class RedisCacheConfiguration {

  private final RedisConnectionFactory redisConnectionFactory;

  /*
   * Time To Live(TTL):
   * A good practice for caching is to ensure that excess and redundant data is not accumulated indefinitely,
   * as this can result in stale or outdated data being served to users.
   * To serve this, we can take advantage of the time-to-live property,
   * which is an optional setting that allows us to set the expiration time for cached data.
   *
   * After the specified time has elapsed, the cached entry is automatically removed from the cache.
   * This makes space for new data to be fetched and stored in the cache the next time it’s requested.
   * If no value is assigned to the property, it becomes -1 by default,
   * which means the data will stay in the cache indefinitely.
   * ttl property to be 60000 ms in the example, which means that the data will be cleared from the cache after every minute.
   */

  @Value("${cache.redis.ttl.seconds:60}")
  private Long ttlInSeconds;

  @Value("${cache.redis.name:user_cache}")
  private String cacheName;

  @Bean
  public CacheManager redisCacheManager() {
    RedisSerializationContext.SerializationPair<Object> jsonSerializer =
        RedisSerializationContext.SerializationPair.fromSerializer(
            new GenericJackson2JsonRedisSerializer());
    return RedisCacheManager.RedisCacheManagerBuilder.fromConnectionFactory(redisConnectionFactory)
        .cacheDefaults(
            org.springframework.data.redis.cache.RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofDays(1))
                .serializeValuesWith(jsonSerializer))
        .build();
  }

  @Bean
  public RedisCacheManagerBuilderCustomizer redisCacheManagerBuilderCustomizer() {
    return (builder) ->
        builder.withCacheConfiguration(
            cacheName,
            org.springframework.data.redis.cache.RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofSeconds(ttlInSeconds))
                .disableCachingNullValues());
  }
}
