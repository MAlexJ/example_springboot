package com.malex.redis_data_store_for_cache.cache.config;

import java.time.Duration;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.cache.RedisCacheManagerBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/*
 * Spring Boot Cache with Redis
 *
 * link:
 */
@Configuration
@RequiredArgsConstructor
public class CacheConfiguration {

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
  public RedisCacheManagerBuilderCustomizer redisCacheManagerBuilderCustomizer() {

    var stringSerializationPair =
            RedisSerializationContext.SerializationPair.fromSerializer(
                    new StringRedisSerializer());

    return (builder) ->
        builder.withCacheConfiguration(
            cacheName,
            RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofSeconds(ttlInSeconds))
                .serializeKeysWith(stringSerializationPair)
                // .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()))
                .disableCachingNullValues());
  }
}
