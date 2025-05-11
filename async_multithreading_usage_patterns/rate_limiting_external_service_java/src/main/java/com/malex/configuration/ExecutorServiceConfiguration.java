package com.malex.configuration;

import java.util.concurrent.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ExecutorServiceConfiguration {

  /*
   * Spring core. Default @Bean destroy method
   *
   * destroyMethod = "shutdown":
   *
   * This specifies the method that should be called when the application context is being shut down.
   * In this case, the method named "shutdown" will be invoked.
   *  This is useful for performing clean-up operations like closing resources
   * (e.g., shutting down a thread pool or closing a database connection).
   *
   */
  @Bean(destroyMethod = "shutdown")
  public ExecutorService runAsyncTasksElasticExecutor() {
    return createElasticExecutor();
  }

  private ThreadPoolExecutor createElasticExecutor() {
    int threads = 10;
    int queueCapacity = 100;

    BlockingQueue<Runnable> queue = new ArrayBlockingQueue<>(queueCapacity);

    var threadPoolExecutor =
        new ThreadPoolExecutor(
            threads, threads, 60L, TimeUnit.SECONDS, queue, new ThreadPoolExecutor.AbortPolicy());

    threadPoolExecutor.allowCoreThreadTimeOut(true);

    return threadPoolExecutor;
  }
}
