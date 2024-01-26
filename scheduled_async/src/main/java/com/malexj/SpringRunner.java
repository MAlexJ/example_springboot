package com.malexj;

import java.util.concurrent.TimeUnit;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringApplication;

public abstract class SpringRunner {

  abstract Class<?> initPrimarySourceClass();

  abstract String[] initMainArgs();

  @Test
  public void run() {
    SpringApplication.run(initPrimarySourceClass(), initMainArgs());
    sleepOneMinutes();
  }

  @SneakyThrows
  public void sleepOneMinutes() {
    TimeUnit.SECONDS.sleep(60);
  }
}
