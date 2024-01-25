package com.malexj;

import static com.malexj.utils.ProfileHelper.buildActiveProfile;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Action:
 *
 * <p>Apply configuration ScheduledTaskRegistrar in {@link
 * com.malexj.configuration.SchedulingConfig}, add Executor
 */
@EnableScheduling
@SpringBootApplication
public class SchedulingConfigurerApplication extends SpringRunner {

  /** Run scheduler class {@link com.malexj.scheduler.SchedulerService} with profile */
  private static final String ACTIVE_PROFILE = "scheduling-configurer";

  @Override
  Class<?> initPrimarySourceClass() {
    return SchedulingConfigurerApplication.class;
  }

  @Override
  String[] initMainArgs() {
    return new String[] {buildActiveProfile(ACTIVE_PROFILE)};
  }
}
