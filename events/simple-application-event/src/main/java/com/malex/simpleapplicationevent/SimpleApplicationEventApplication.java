package com.malex.simpleapplicationevent;

import com.malex.simpleapplicationevent.base.AbstractSpringBootRunnerUtils;
import com.malex.simpleapplicationevent.publisher.SimpleSpringEventPublisher;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Profile;

/** Spring allows us to create and publish custom events that by default are synchronous */
@Profile("simple")
@Slf4j
@RequiredArgsConstructor
@SpringBootApplication
public class SimpleApplicationEventApplication extends AbstractSpringBootRunnerUtils
    implements CommandLineRunner {

  private static final String ACTIVE_PROFILE = "simple";

  @Autowired private SimpleSpringEventPublisher simpleSpringEventPublisher;

  @Override
  public void run(String... args) {
    /* 1. publish simple event */
    simpleSpringEventPublisher.publishEvent("{json}", UUID.randomUUID().toString());
  }

  @Override
  public Class<?> initPrimarySourceClass() {
    return SimpleApplicationEventApplication.class;
  }

  @Override
  public String[] initMainArgs() {
    return new String[] {buildActiveProfile(ACTIVE_PROFILE)};
  }

  @Override
  protected long shutdown() {
    return 2000;
  }
}
