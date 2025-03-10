### Spring Boot 3 and JDK 23 support virtual threads

Spring Boot 3 and JDK 23 support virtual threads, but SimpleAsyncTaskExecutor does not use virtual threads by default.
It spawns new platform threads instead. If you want to leverage virtual threads for event handling, try configuring
ApplicationEventMulticaster with a virtual thread executor:

```
@Bean
public ApplicationEventMulticaster applicationEventMulticaster() {
    
    var eventMulticaster = new SimpleApplicationEventMulticaster();
    eventMulticaster.setTaskExecutor(Executors.newVirtualThreadPerTaskExecutor());
    
    return eventMulticaster;
}
```

link: https://www.baeldung.com/spring-6-virtual-threads