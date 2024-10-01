### Asynchronous process start by signal

link: https://habr.com/ru/articles/838402/

Perhaps this is the simplest pattern to start with

The signal can be any event that does not wait for the result of the execution of the scenario, but only launches it,
for example:

* HTTP message - HTTP request for analytical report is sent, the report will be generated in a few minutes, client
  service
  does not want to wait so long, it only needs to start process

* Message from broker (Kafka, IBM MQ, RabbitMQ) - we don’t always want to process the message in the same stream it
  comes
  in, when high loads is better to switch to separate pool of streams so as not to block reading new messages (in the
  same
  kafka, the number of read threads is equal to the number of partitions subscribed to by the application, but they are
  usually few)

* Run event on time - for example, perform a recount to DB

Executor configuration:

```
@Bean(destroyMethod = "shutdown")
public ExecutorService elasticExecutor() {
    return createElasticExecutor(10, 100);
}

private ThreadPoolExecutor createElasticExecutor(int threads, int queueCapacity) {
    BlockingQueue<Runnable> queue = new ArrayBlockingQueue<>(queueCapacity);

    ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
        threads, threads,
        60L, TimeUnit.SECONDS,
        queue, new ThreadPoolExecutor.AbortPolicy());

    threadPoolExecutor.allowCoreThreadTimeOut(true);

    return threadPoolExecutor;
}
```

1. Directly call executorService.execute(() -> { … }):

```
public void runWithStraightExecuteMethod() {
    runAsyncTasksElasticExecutor.execute(() -> {
        executeLongOperation();
    });
}
```

2. Using @Async annotation

```
@Async("runAsyncTasksElasticExecutor")
public void runWithAnnotation() {
    executeLongOperation();
}
```

3. Using CompletableFuture

```
public void runWithCompletableFuture() {
    CompletableFuture.runAsync(() -> {
            executeLongOperation();
        },
        runAsyncTasksElasticExecutor);
}
```