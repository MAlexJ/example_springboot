package com.malexj.scheduler;

import java.time.LocalTime;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Profile({
  "pool-size-profile",
  "scheduling-configurer",
  "default-config",
  "virtual-scheduling-configurer"
})
public class SchedulerService {

  @Scheduled(cron = "*/1 * * * * *")
  public void everySecond() {
    log.info(
        ">>> EverySecond. time: {}. thread: {}", LocalTime.now(), Thread.currentThread().getName());
  }

  @Scheduled(cron = "*/5 * * * * *")
  public void everyFiveSeconds() throws InterruptedException {
    log.info(
        "  <<< EveryFiveSeconds. time: {}. time: {}",
        LocalTime.now(),
        Thread.currentThread().getName());
    Thread.sleep(5000);
  }
}
