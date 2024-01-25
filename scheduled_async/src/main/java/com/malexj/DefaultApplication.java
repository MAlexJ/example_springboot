package com.malexj;

import static com.malexj.utils.ProfileHelper.buildActiveProfile;

import java.util.concurrent.TimeUnit;
import lombok.SneakyThrows;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Problem:
 *
 * <p>There are two scheduler job that running in one 'thread: scheduling-1' thread. <br>
 * If the first job is suspended then second job is not done. <br>
 * The work that is done by job blocks the execution of the other. <br>
 */
@EnableScheduling
@SpringBootApplication
public class DefaultApplication {

  /** Run scheduler class {@link com.malexj.scheduler.SchedulerService} with profile */
  private static final String ACTIVE_PROFILE = "default-config";

  public static void main(String[] args) {
    SpringApplication.run(DefaultApplication.class, buildActiveProfile(ACTIVE_PROFILE));
  }

  public static void run(Class<?> primarySource, String... args) {
    SpringApplication.run(primarySource, args);
  }

  @SneakyThrows
  public static void sleepOneSecond() {
    TimeUnit.SECONDS.sleep(60);
  }
}
