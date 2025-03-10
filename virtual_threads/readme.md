### Working with Virtual Threads in Spring

link: https://www.baeldung.com/spring-6-virtual-threads

#### 1. Virtual Threads With Spring Boot 3.2 and Java 21

Starting from Spring Boot 3.2, enabling virtual threads is very easy if we’re using Java 21.
We set the spring.threads.virtual.enabled property to true, and we’re good to go:

`spring.threads.virtual.enabled=true`

Theoretically, we don’t have to do anything else.
However, switching from normal threads to virtual threads can have unforeseen consequences for legacy applications.
Therefore, we must test our application thoroughly.

#### 2. From the Java point of view, to work with Apache Tomcat and virtual threads

From the Java point of view, to work with Apache Tomcat and virtual threads, we need a simple configuration class with a
couple of beans:

```
@EnableAsync
@Configuration
@ConditionalOnProperty(value = "spring.thread-executor",havingValue = "virtual")
public class ThreadConfig {
    
    @Bean
    public AsyncTaskExecutor applicationTaskExecutor() {
       return new TaskExecutorAdapter(Executors.newVirtualThreadPerTaskExecutor());
    }

    @Bean
    public TomcatProtocolHandlerCustomizer<?> protocolHandlerVirtualThreadExecutorCustomizer() {
        return protocolHandler -> {
            protocolHandler.setExecutor(Executors.newVirtualThreadPerTaskExecutor());
        };
    }

}
```

The first Spring Bean, ApplicationTaskExecutor, replaces the standard ApplicationTaskExecutor. In short, we want to
override the default Executor so it starts a new virtual thread for each task. The second bean, named
ProtocolHandlerVirtualThreadExecutorCustomizer, customizes the standard TomcatProtocolHandler in the same way.

Additionally, we add the annotation @ConditionalOnProperty so we can enable or disable virtual threads using properties
in the application.yaml file:

```
spring:
    thread-executor: virtual
```

Now, we can verify that we are running virtual threads.

#### 3. Verify Virtual Threads Are Running

Let’s test whether the Spring Boot Application uses virtual threads to handle web request calls.
To do this, we need to build a simple controller that returns the required information:

```
@RestController
@RequestMapping("/thread")
public class ThreadController {

  @GetMapping("/name")
  public String getThreadName() {
    return Thread.currentThread().toString();
  }

}
```

The toString() method of the Thread object returns all the information we need:
the thread id, thread name, thread group, and priority. Let’s hit this endpoint with a curl request:

```
$ curl -s http://localhost:8080/thread/name
$ VirtualThread[#171]/runnable@ForkJoinPool-1-worker-4
```
