### Parallel execution of tasks without waiting for the result

link: https://habr.com/ru/articles/838402/

This pattern is an alternative to the previous one, but still worth considering because it is often found in practice

Let’s say you received a message that you need to send to different external sources via HTTP or another protocol.
We do not care about the order in which the sending will be executed,
and also the results of each send are not dependent on the results of previous (independent)

This means that these tasks can be executed in parallel streams

First, we will create an interface with a single method whose destination is to send a message to the external service:

```
public interface MessageSender {
	void send(Message message);
}
```

service example:

```
@Slf4j
@Service
public class MessageSender1implements MessageSender {

    @Override
	public void send(Message message) {
        // Выполнение операции отправки
        MentoringUtil.sleep(1500);

        log.info("Сообщение отправлено в источник #1");
    }
}

```

Sender

```
@Service
@RequiredArgsConstructor
public class SendInParallelExample {

    private final List<MessageSender> messageSenders;
	private final ExecutorService runParallelTasksElasticExecutor;

	public void sendMessageToSeveralTargets(Message message) {
        messageSenders.forEach(messageSender ->
            CompletableFuture.runAsync(() -> 
    		    messageSender.send(message), runParallelTasksElasticExecutor)
        );
    }
}
```

Code Explanations:

* Import collection of binaries implementing MessageSender interface and bin ExecutorService, in which the sender code
  execution will take place

* We run through the MessageSender's, and for each one we execute an asynchronous call
  CompletableFuture.runAsync(...), without waiting for a result, with the second parameter we pass ExecutorService so
  that the execution takes place in its streams

This way we can run parallel tasks.
We can use the other approaches given in the previous pattern (direct call executorService.execute(... )
or @Async annotation

Note:

A couple of words about parallelStream. We could do the same thing via . parallelStream(). forEach(...),
without using CompletableFuture, but this approach is not suitable for parallel execution
of blocking operations (such as external Rest calls) because parallelStream uses ForkJoinPool,
the number of threads is limited by the number of cores, which can lead to low performance