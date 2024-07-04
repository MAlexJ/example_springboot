package com.malex.simpleapplicationevent.base;

import java.util.concurrent.TimeUnit;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringApplication;

public abstract class AbstractSpringBootRunnerUtils {

  protected static final String PROFILE_TEMPLATE = "--spring.profiles.active=%s";

  protected String buildActiveProfile(String profile) {
    return String.format(PROFILE_TEMPLATE, profile);
  }

  protected abstract Class<?> initPrimarySourceClass();

  protected abstract String[] initMainArgs();

  protected abstract long shutdown();

  @Test
  public void run() {
    SpringApplication.run(initPrimarySourceClass(), initMainArgs());
    sleep();
  }

  @SneakyThrows
  private void sleep() {
    TimeUnit.MILLISECONDS.sleep(shutdown());
  }
}
