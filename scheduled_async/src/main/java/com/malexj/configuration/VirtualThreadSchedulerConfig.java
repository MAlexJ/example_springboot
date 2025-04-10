package com.malexj.configuration;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

/*
 * Task Execution and Scheduling
 * In the absence of an Executor bean in the context, Spring Boot auto-configures an AsyncTaskExecutor.
 * When virtual threads are enabled (using Java 21+ and spring.threads.virtual.enabled set to true)
 *  this will be a SimpleAsyncTaskExecutor that uses virtual threads
 */
@Configuration
@Profile("virtual-scheduling-configurer")
public class VirtualThreadSchedulerConfig implements SchedulingConfigurer {

  @Override
  public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
    taskRegistrar.setScheduler(virtualThreadScheduler());
  }

  private ScheduledExecutorService virtualThreadScheduler() {
    return Executors.newScheduledThreadPool(
        Runtime.getRuntime().availableProcessors(), new NamedVirtualThreadFactory("x-v-sch"));
  }

  static class NamedVirtualThreadFactory implements ThreadFactory {
    private final String prefix;
    private final AtomicInteger counter = new AtomicInteger(1);

    NamedVirtualThreadFactory(String prefix) {
      this.prefix = prefix;
    }

    @Override
    public Thread newThread(Runnable r) {
      Thread thread = Thread.ofVirtual().unstarted(r);
      thread.setName(prefix + "-" + counter.getAndIncrement());
      return thread;
    }
  }
}
