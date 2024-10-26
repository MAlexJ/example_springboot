package com.malex.synchronous;

import com.malex.synchronous.base.AbstractSpringBootRunnerUtils;
import com.malex.synchronous.publisher.SynchronousEventPublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Profile;

@Profile("synchronous")
@SpringBootApplication
public class SynchronousEventApplication extends AbstractSpringBootRunnerUtils
    implements CommandLineRunner {

  private static final String ACTIVE_PROFILE = "synchronous";

  @Autowired private SynchronousEventPublisher publisher;

  @Override
  protected Class<?> initPrimarySourceClass() {
    return SynchronousEventApplication.class;
  }

  @Override
  protected String activeProfile() {
    return ACTIVE_PROFILE;
  }

  /*
   * publish simple event
   */
  @Override
  public void run(String... args) {
    publisher.publishEvent("Hello new event!");
  }
}
