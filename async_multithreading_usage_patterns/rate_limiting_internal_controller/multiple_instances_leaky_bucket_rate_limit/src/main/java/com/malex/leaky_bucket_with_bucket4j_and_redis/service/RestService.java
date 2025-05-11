package com.malex.leaky_bucket_with_bucket4j_and_redis.service;

import java.util.concurrent.TimeUnit;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

@Service
public class RestService {

  @SneakyThrows
  public String process(String value) {
    TimeUnit.SECONDS.sleep(5);
    return value.toUpperCase();
  }
}
