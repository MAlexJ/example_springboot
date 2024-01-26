package com.malexj.dmdevbasespringbootcourse;

import java.util.concurrent.TimeUnit;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringApplication;

public abstract class SpringRunner {

  private static final String PROFILE_TEMPLATE = "--spring.profiles.active=%s";

  public abstract Class<?> initPrimarySourceClass();

  public abstract String[] initArgs();

  public abstract long serverUpTimeInSecond();

  @Test
  public void run() {
    SpringApplication.run(initPrimarySourceClass(), initArgs());
    sleep();
  }

  @SneakyThrows
  public void sleep() {
    TimeUnit.SECONDS.sleep(serverUpTimeInSecond());
  }

  public String[] applyActiveProfile(String profile) {
    return new String[] {String.format(PROFILE_TEMPLATE, profile)};
  }
}
