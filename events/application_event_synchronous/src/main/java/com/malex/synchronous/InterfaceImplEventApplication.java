package com.malex.synchronous;

import com.malex.synchronous.base.AbstractSpringBootRunnerUtils;
import com.malex.synchronous.publisher.InterfaceImplEventPublisher;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Profile;

/** Spring allows us to create and publish custom events that by default are synchronous */
@Profile("interface")
@SpringBootApplication
public class InterfaceImplEventApplication extends AbstractSpringBootRunnerUtils
    implements CommandLineRunner {

  private static final String ACTIVE_PROFILE = "interface";

  @Autowired private InterfaceImplEventPublisher publisher;

  /*
   * publish simple event
   */
  @Override
  public void run(String... args) {
    publisher.publishEvent(UUID.randomUUID().toString());
  }

  @Override
  public Class<?> initPrimarySourceClass() {
    return InterfaceImplEventApplication.class;
  }

  @Override
  protected String activeProfile() {
    return ACTIVE_PROFILE;
  }
}
