package com.malex.leaky_bucket_with_bucket4j_and_redis.configuration;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.BucketConfiguration;
import io.github.bucket4j.distributed.ExpirationAfterWriteStrategy;
import io.github.bucket4j.distributed.proxy.ClientSideConfig;
import io.github.bucket4j.distributed.proxy.ProxyManager;
import io.github.bucket4j.redis.lettuce.cas.LettuceBasedProxyManager;
import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.codec.ByteArrayCodec;
import io.lettuce.core.codec.RedisCodec;
import io.lettuce.core.codec.StringCodec;
import java.time.Duration;
import java.util.function.Supplier;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class BucketRateLimitConfiguration {

  /*
   * Specifying capacity in terms of Token-Bucket
   * Params:
   * tokens – capacity in terms of Token-Bucket
   */
  @Value("${token.bucket.capacity}")
  private long capacity;

  /*
   * tokens – amount of tokens
   *
   * Configures refill that does refill of tokens in greedy manner,
   * it will try to add the tokens to bucket as soon as possible.
   */
  @Value("${token.bucket.refill.tokens}")
  private long tokens;

  /*
   * period – the period within tokens will be fully regenerated
   *
   * Configures refill that does refill of tokens in greedy manner,
   * it will try to add the tokens to bucket as soon as possible.
   */
  @Value("${token.bucket.refill.period}")
  private long period;

  private final RedisClient redisClient;

  @Bean
  public ProxyManager<String> lettuceBasedProxyManager() {

    StatefulRedisConnection<String, byte[]> redisConnection =
        redisClient.connect(RedisCodec.of(StringCodec.UTF8, ByteArrayCodec.INSTANCE));

    var expirationAfterWriteStrategy =
        ExpirationAfterWriteStrategy.basedOnTimeForRefillingBucketUpToMax(Duration.ofSeconds(1L));

    var clientSideConfig =
        ClientSideConfig.getDefault()
            .withExpirationAfterWriteStrategy(expirationAfterWriteStrategy);

    return LettuceBasedProxyManager.builderFor(redisConnection)
        .withClientSideConfig(clientSideConfig)
        .build();
  }

  @Bean
  public Supplier<BucketConfiguration> bucketConfiguration() {
    var build =
        Bandwidth.builder()
            /*
             * Specifying capacity in terms of Token-Bucket
             */
            .capacity(capacity)
            /*
             * Configures refill that does refill of tokens in greedy manner,
             * it will try to add the tokens to bucket as soon as possible.
             *
             * For example refill "10 tokens per 1 second" will add 1 token per each 100 millisecond,
             * in other words refill will not wait 1 second to regenerate a bunch of 10 tokens.
             */
            .refillGreedy(tokens, Duration.ofSeconds(period))
            .build();
    return () -> BucketConfiguration.builder().addLimit(build).build();
  }
}
