package com.malexj;

import static com.malexj.utils.ProfileHelper.buildActiveProfile;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Configuration:
 *
 * <p>application.yaml add --spring.task.scheduling.pool.size=2
 */
@EnableScheduling
@SpringBootApplication
public class SchedulingPoolSizeApplication extends SpringRunner {

  /** Run scheduler class {@link com.malexj.scheduler.SchedulerService} with profile */
  private static final String ACTIVE_PROFILE = "pool-size-profile";

  private static final String SPRING_TASK_SCHEDULING_POOL_SIZE =
      "--spring.task.scheduling.pool.size=3";

  @Override
  Class<?> initPrimarySourceClass() {
    return SchedulingPoolSizeApplication.class;
  }

  @Override
  String[] initMainArgs() {
    return new String[] {buildActiveProfile(ACTIVE_PROFILE), SPRING_TASK_SCHEDULING_POOL_SIZE};
  }
}
