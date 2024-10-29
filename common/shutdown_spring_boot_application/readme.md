### Shutdown a Spring Boot Application

Managing the lifecycle of a Spring Boot Application is very important for a production-ready system.
Spring container handles the creation, initialization, and destruction of all the Beans with help of ApplicationContext.

The focus of this tutorial is the destruction phase of the lifecycle.
More specifically, we’ll look at different ways to shut down a Spring Boot Application.

link: https://www.baeldung.com/spring-boot-shutdown

#### 1. Actuator Shutdown Endpoint

By default, all the endpoints are enabled in a Spring Boot Application except /shutdown,
which is part of the Actuator endpoints.

Finally, we’ll enable the shutdown endpoint in the application.properties file:

```
management.endpoints.web.exposure.include=*
management.endpoint.shutdown.enabled=true
```

Note that we also have to expose any actuator endpoints that we want to use.

In the example above, we exposed all the actuator endpoints, which includes the /shutdown endpoint.

To shut down the Spring Boot application, we’ll simply call a POST method like this:

```
curl -X POST localhost:port/actuator/shutdown
```

#### 2. Close Application Context

We can also call the close() method directly using the application context.

Let’s start with an example of creating a context and closing it:

```
ConfigurableApplicationContext ctx = new
SpringApplicationBuilder(Application.class).web(WebApplicationType.NONE).run();
System.out.println("Spring Boot application started");
ctx.getBean(TerminateBean.class);
ctx.close();
```

This destroys all the beans, releases the locks, and then closes the bean factory.
To verify the application shutdown, we’ll use Spring’s standard lifecycle callback with the @PreDestroy annotation:

```
public class TerminateBean {

    @PreDestroy
    public void onDestroy() throws Exception {
        System.out.println("Spring Container is destroyed!");
    }
}
```

We also have to add a bean of this type:

```
@Configuration
public class ShutdownConfig {

    @Bean
    public TerminateBean getTerminateBean() {
        return new TerminateBean();
    }
}
```

#### 3. Close the Current Application Context

In the example above, we created a child application context, then used the close() method to destroy it.

If we want to close the current context, one solution is to simply call the actuator /shutdown endpoint.

However, we can also create our own custom endpoint:

```
@RestController
public class ShutdownController implements ApplicationContextAware {

    private ApplicationContext context;
    
    @PostMapping("/shutdownContext")
    public void shutdownContext() {
        ((ConfigurableApplicationContext) context).close();
    }

    @Override
    public void setApplicationContext(ApplicationContext ctx) throws BeansException {
        this.context = ctx;
        
    }
}
```

Here, we added a controller that implements the ApplicationContextAware interface and overrides the setter method
to obtain the current application context. Then, in a mapping method, we simply call the close() method.

We can then call our new endpoint to shut down the current context:

#### 4. Exit SpringApplication

SpringApplication registers a shutdown hook with the JVM to make sure the application exits appropriately.

Beans may implement the ExitCodeGenerator interface to return a specific error code:

```
ConfigurableApplicationContext ctx = new SpringApplicationBuilder(Application.class)
.web(WebApplicationType.NONE).run();

int exitCode = SpringApplication.exit(ctx, new ExitCodeGenerator() {
@Override
public int getExitCode() {
// return the error code
return 0;
}
});

System.exit(exitCode);
```

Here’s the same code with the application of Java 8 lambdas:

```
SpringApplication.exit(ctx, () -> 0);
```

After calling the System.exit(exitCode), the program terminates with a 0 return code:

```
Process finished with exit code 0
```

#### 5.Kill the App Process

Finally, we can also shut down a Spring Boot Application from outside the application by using a bash script.
Our first step for this option is to have the application context write it’s PID into a file:

```
SpringApplicationBuilder app = new SpringApplicationBuilder(Application.class)
.web(WebApplicationType.NONE);
app.build().addListeners(new ApplicationPidFileWriter("./bin/shutdown.pid"));
app.run();
```

Next, we’ll create a shutdown.bat file with the following content:

```
kill $(cat ./bin/shutdown.pid)
```

The execution of shutdown.bat extracts the Process ID from the shutdown.pid file and uses the kill command to terminate
the Boot application.