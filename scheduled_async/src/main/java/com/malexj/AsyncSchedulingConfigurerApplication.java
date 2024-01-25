package com.malexj;

import static com.malexj.utils.ProfileHelper.buildActiveProfile;

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
public class AsyncSchedulingConfigurerApplication extends SpringRunner {

  /**
   * Profiler for {@link com.malexj.scheduler.AsyncSchedulerService} and {@link
   * com.malexj.configuration.AsyncSchedulingConfig} with @Async annotation
   */
  private static final String ACTIVE_PROFILE = "async-scheduling-config";

  @Override
  Class<?> initPrimarySourceClass() {
    return AsyncSchedulingConfigurerApplication.class;
  }

  @Override
  String[] initMainArgs() {
    return new String[] {buildActiveProfile(ACTIVE_PROFILE)};
  }
}
