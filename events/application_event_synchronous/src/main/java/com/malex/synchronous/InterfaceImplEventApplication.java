package com.malex.synchronous;

import com.malex.synchronous.base.AbstractSpringBootRunnerUtils;
import com.malex.synchronous.publisher.InterfaceImplEventPublisher;
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
public class InterfaceImplEventApplication extends AbstractSpringBootRunnerUtils
    implements CommandLineRunner {

  private static final String ACTIVE_PROFILE = "simple";

  @Autowired private InterfaceImplEventPublisher publisher;

  @Override
  public void run(String... args) {
    /*
     * publish simple event
     */
    publisher.publishEvent("{json_body}", UUID.randomUUID().toString());
  }

  @Override
  public Class<?> initPrimarySourceClass() {
    return InterfaceImplEventApplication.class;
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
