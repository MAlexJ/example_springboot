### Virtual Thread Executor Bean (JDK 21+)

----------------------------------------------------------------------------------------------------------

1. Configure Spring to Use a Virtual Thread Executor

Note: add **@EnableAsync** annotation

simple configuration:

```
@EnableAsync
@Configuration
public class AsyncConfig {

    @Bean(name = "virtualThreadExecutor", destroyMethod = "shutdown")
    public ExecutorService virtualThreadExecutor() {
        return Executors.newVirtualThreadPerTaskExecutor();
    }
}
```

Explanation:

* Executors.newVirtualThreadPerTaskExecutor() returns an ExecutorService that:
    - Creates a new virtual thread for every task.
    - Is ideal for concurrent I/O-bound tasks (e.g., HTTP calls, database queries).

* Virtual threads are lightweight and scale well compared to platform (OS) threads.

* shutdown() is still valid — this executor also needs to be shut down to clean up thread factories.

----------------------------------------------------------------------------------------------------------

2. Using @Async annotation

2.1. void return:

If you don’t care about the result or status of the task, you can simply use:

```
@Async("virtualThreadExecutor")
public void runAsyncTask() {
  // fire-and-forget task
}
```

This is clean for "fire-and-forget" use cases.

2.2. CompletableFuture return:

Use this only when the caller needs to:

* Get the result later (async result pattern),
* Chain callbacks, or
* Wait for the task to finish explicitly.

```
@Async("virtualThreadExecutor")
public CompletableFuture<String> runAsyncTask() {
    return CompletableFuture.completedFuture("Result");
}
```

This allows the caller to do:

```
CompletableFuture<String> result = myAsyncService.runAsyncTask();
result.thenAccept(System.out::println);
```

This is useful when the result matters to the rest of the workflow.