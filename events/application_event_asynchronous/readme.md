### Spring Asynchronous Events

#### Asynchronous Events configuration

1. @Bean(name = "applicationEventMulticaster") with name
2. Configuration class

```
@Configuration
public class AsynchronousSpringEventsConfig {
    @Bean(name = "applicationEventMulticaster")
    public ApplicationEventMulticaster simpleApplicationEventMulticaster() {
        SimpleApplicationEventMulticaster eventMulticaster =
          new SimpleApplicationEventMulticaster();
        
        eventMulticaster.setTaskExecutor(new SimpleAsyncTaskExecutor());
        return eventMulticaster;
    }
}
```

The event, the publisher and the listener implementations remain the same as before,
but now the listener will asynchronously deal with the event in a separate thread.

link: https://www.baeldung.com/spring-events#anonymous-events