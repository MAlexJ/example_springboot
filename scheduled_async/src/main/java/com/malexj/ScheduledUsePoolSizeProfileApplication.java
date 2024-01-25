package com.malexj;

import static com.malexj.utils.ProfileHelper.buildActiveProfile;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class ScheduledUsePoolSizeProfileApplication {

  /** Run scheduler class {@link com.malexj.scheduler.SchedulerService} with profile */
  private static final String ACTIVE_PROFILE = "pool-size-profile";

  private static final String SPRING_TASK_SCHEDULING_POOL_SIZE =
      "--spring.task.scheduling.pool.size=2";

  public static void main(String[] args) {
    SpringApplication.run(
        ScheduledUsePoolSizeProfileApplication.class,
        buildActiveProfile(ACTIVE_PROFILE),
        SPRING_TASK_SCHEDULING_POOL_SIZE);
  }
}
