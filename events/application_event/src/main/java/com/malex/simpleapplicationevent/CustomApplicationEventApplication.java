package com.malex.simpleapplicationevent;

import com.malex.simpleapplicationevent.base.AbstractSpringBootRunnerUtils;
import com.malex.simpleapplicationevent.publisher.CustomSpringEventPublisher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Profile;

@Profile("custom")
@Slf4j
@SpringBootApplication
public class CustomApplicationEventApplication extends AbstractSpringBootRunnerUtils
    implements CommandLineRunner {

  private static final String ACTIVE_PROFILE = "custom";

  @Autowired private CustomSpringEventPublisher customSpringEventPublisher;

  @Override
  public void run(String... args) {
    /* publish custom event */
    customSpringEventPublisher.publishEvent("Custom hello!");
  }

  @Override
  protected Class<?> initPrimarySourceClass() {
    return CustomApplicationEventApplication.class;
  }

  @Override
  protected String[] initMainArgs() {
    return new String[] {buildActiveProfile(ACTIVE_PROFILE)};
  }

  @Override
  protected long shutdown() {
    return 2000;
  }
}
