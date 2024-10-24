package com.malex.synchronous;

import com.malex.synchronous.base.AbstractSpringBootRunnerUtils;
import com.malex.synchronous.publisher.AnnotationImplEventPublisher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Profile;

@Profile("annotation")
@Slf4j
@SpringBootApplication
public class AnnotationImlEventApplication extends AbstractSpringBootRunnerUtils
    implements CommandLineRunner {

  private static final String ACTIVE_PROFILE = "annotation";

  @Autowired private AnnotationImplEventPublisher publisher;

  @Override
  public void run(String... args) {
    /*
     * publish custom event
     */
    publisher.publishEvent("Custom hello!");
  }

  @Override
  protected Class<?> initPrimarySourceClass() {
    return AnnotationImlEventApplication.class;
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
