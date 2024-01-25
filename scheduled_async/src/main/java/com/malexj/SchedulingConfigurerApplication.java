package com.malexj;

import static com.malexj.utils.ProfileHelper.buildActiveProfile;

import org.springframework.boot.SpringApplication;
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
public class SchedulingConfigurerApplication {

  /**
   * Run scheduler two classes: {@link com.malexj.scheduler.SchedulerService} and {@link
   * com.malexj.configuration.SchedulingConfig} with profile
   */
  private static final String ACTIVE_PROFILE = "scheduling-configurer";

  public static void main(String[] args) {
    SpringApplication.run(
        SchedulingConfigurerApplication.class, buildActiveProfile(ACTIVE_PROFILE));
  }
}
