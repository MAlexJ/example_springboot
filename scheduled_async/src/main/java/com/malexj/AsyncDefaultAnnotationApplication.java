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
 * 2. mark scheduler job with @Async annotation
 *
 * <p>Note:<br>
 * 1. Using the annotation @Async by default, Spring creates an Executor that has no limit on the
 * number of threads created. <br>
 * This way, if your gob is launched more often than it is terminated, there will be a memory leak
 * due to constantly created threads. <br>
 * You can solve the problem by configuring Executor for @Async, an example configuration can be
 * found in the javadoc annotation @EnableAsync.
 *
 * <p>Note:<br>
 * 1. If not found in context: springframework.core.task.TaskExecutor or
 * java.util.concurrent.Executor, then SimpleAsyncTaskExecutor will be used to process async method
 * invocations.<br>
 * 2. By default, for org.springframework.core.task.SimpleAsyncTaskExecutor , the number of
 * concurrent task executions is unlimited.
 */
@EnableAsync
@EnableScheduling
@SpringBootApplication
public class AsyncDefaultAnnotationApplication extends SpringRunner {

  /** Profile for {@link com.malexj.scheduler.AsyncSchedulerService} with @Async annotation */
  private static final String ACTIVE_PROFILE = "async-default-config";

  @Override
  Class<?> initPrimarySourceClass() {
    return AsyncDefaultAnnotationApplication.class;
  }

  @Override
  String[] initMainArgs() {
    return new String[] {buildActiveProfile(ACTIVE_PROFILE)};
  }
}
