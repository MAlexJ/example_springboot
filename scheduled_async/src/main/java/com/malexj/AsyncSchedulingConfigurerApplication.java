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
 * 2. mark scheduler job with @Async annotation<br>
 * 3. configure ThreadPoolTaskExecutor in class {@link
 * com.malexj.configuration.AsyncSchedulingConfig}
 */
@EnableAsync
@EnableScheduling
@SpringBootApplication
public class AsyncSchedulingConfigurerApplication {

  /** Run scheduler class {@link com.malexj.scheduler.SchedulerService} with profile */
  private static final String ACTIVE_PROFILE = "async-scheduling-config";

  public static void main(String[] args) {
    SpringApplication.run(
        AsyncSchedulingConfigurerApplication.class, buildActiveProfile(ACTIVE_PROFILE));
  }
}
