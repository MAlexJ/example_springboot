package com.malex.leaky_bucket_with_bucket4j_and_redis;

import com.malex.leaky_bucket_with_bucket4j_and_redis.configuration.BucketRateLimitConfiguration;
import com.malex.leaky_bucket_with_bucket4j_and_redis.filter.RateLimitFilter;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

/*
 * Ignore filter and configuration classes
 */
@MockBean(classes = {BucketRateLimitConfiguration.class, RateLimitFilter.class})
@SpringBootTest
class LeakyBucketWithBucket4jAndRedisApplicationTests {

  @Test
  void contextLoads() {}
}
