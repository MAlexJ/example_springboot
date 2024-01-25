package com.malexj;

import static com.malexj.utils.ProfileHelper.buildActiveProfile;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Link to info: https://habr.com/ru/articles/771112/
 *
 * <p>Additional configuration: <br>
 * <br>
 * 1. mark main class with @EnableAsync <br>
 * 2. mark scheduler job with @Async annotation
 *
 * <p>Note:<br>
 * <br>
 * Using the annotation @Async by default, Spring creates an Executor that has no limit on the
 * number of threads created. <br>
 * This way, if your gob is launched more often than it is terminated, there will be a memory leak
 * due to constantly created threads. <br>
 * You can solve the problem by configuring Executor for @Async, an example configuration can be
 * found in the javadoc annotation @EnableAsync.
 */
@EnableAsync
@EnableScheduling
@SpringBootApplication
public class AsyncAnnotationApplication {

  /** Run scheduler class {@link com.malexj.scheduler.SchedulerService} with profile */
  private static final String ACTIVE_PROFILE = "async-config";

  public static void main(String[] args) {
    SpringApplication.run(AsyncAnnotationApplication.class, buildActiveProfile(ACTIVE_PROFILE));
  }
}
