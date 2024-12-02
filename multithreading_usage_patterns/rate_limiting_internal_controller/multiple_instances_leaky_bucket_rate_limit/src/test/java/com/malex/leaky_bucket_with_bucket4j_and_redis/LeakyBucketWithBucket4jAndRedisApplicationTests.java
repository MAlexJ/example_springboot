package com.malex.leaky_bucket_with_bucket4j_and_redis;

import com.malex.leaky_bucket_with_bucket4j_and_redis.configuration.BucketRateLimitConfiguration;
import com.malex.leaky_bucket_with_bucket4j_and_redis.filter.RateLimitFilter;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

/*
 * Ignore filter and configuration classes
 */
@SpringBootTest
class LeakyBucketWithBucket4jAndRedisApplicationTests {

  @MockitoBean private BucketRateLimitConfiguration bucketRateLimitConfiguration;

  @MockitoBean private RateLimitFilter rateLimitFilter;

  @Test
  void contextLoads() {}
}
