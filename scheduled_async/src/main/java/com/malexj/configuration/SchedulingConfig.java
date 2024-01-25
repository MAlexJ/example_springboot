package com.malexj.configuration;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

@Configuration
@Profile("scheduling-configurer")
public class SchedulingConfig implements SchedulingConfigurer {

  @Bean
  public Executor taskExecutor() {
    return Executors.newScheduledThreadPool(100);
  }

  @Override
  public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
    taskRegistrar.setScheduler(taskExecutor());
  }
}
