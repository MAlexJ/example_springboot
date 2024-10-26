package com.malex.synchronous;

import com.malex.synchronous.base.AbstractSpringBootRunnerUtils;
import com.malex.synchronous.publisher.AnnotationImplEventPublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Profile;

@Profile("annotation")
@SpringBootApplication
public class AnnotationImlEventApplication extends AbstractSpringBootRunnerUtils
    implements CommandLineRunner {

  private static final String ACTIVE_PROFILE = "annotation";

  @Autowired private AnnotationImplEventPublisher publisher;

  /*
   * publish simple event
   */
  @Override
  public void run(String... args) {
    publisher.publishEvent("Custom hello!");
  }

  @Override
  protected Class<?> initPrimarySourceClass() {
    return AnnotationImlEventApplication.class;
  }

  @Override
  protected String activeProfile() {
    return ACTIVE_PROFILE;
  }
}
