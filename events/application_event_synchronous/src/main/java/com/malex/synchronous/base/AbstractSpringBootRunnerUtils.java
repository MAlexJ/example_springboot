package com.malex.synchronous.base;

import java.util.concurrent.TimeUnit;
import lombok.SneakyThrows;

import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringApplication;

public abstract class AbstractSpringBootRunnerUtils {

  protected static final String PROFILE_TEMPLATE = "--spring.profiles.active=%s";

  private String buildActiveProfile(String profile) {
    return String.format(PROFILE_TEMPLATE, profile);
  }

  protected abstract Class<?> initPrimarySourceClass();

  protected abstract String activeProfile();

  @Test
  public void run() {
    var applicationContext = SpringApplication.run(initPrimarySourceClass(), buildActiveProfile(activeProfile()));
    applicationContext.close();
  }

  @SneakyThrows
  public static void sleep(long millis) {
    TimeUnit.MILLISECONDS.sleep(millis);
  }
}
