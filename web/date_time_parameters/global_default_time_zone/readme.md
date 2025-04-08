### Setting Default TimeZone in Spring Boot Application

Sometimes, we want to be able to specify the TimeZone used by an application.
For a service running globally, this could mean that all servers are posting events using the same TimeZone,
no matter their location.

We can achieve this in a few different ways.
One approach involves the use of JVM arguments when we execute the application.
The other approach is to make the change programmatically in our code at various points of the bootup lifecycle.

1. Setting Default TimeZone on bootRun Task
2. Setting Default TimeZone on JAR Execution
3. Setting Default TimeZone on Spring Boot Startup
    * Main method
    * BeanFactoryPostProcessor
    * PostConstruct

link: https://www.baeldung.com/spring-boot-set-default-timezone

#### 1. Setting Default TimeZone on bootRun Task

If we use the bootRun task to run the application, we can pass the default TimeZone using JVM arguments
in the command line.
In this case, the value we set is available from the very beginning of the execution:

```
mvn spring-boot:run -Dspring-boot.run.jvmArguments="-Duser.timezone=Europe/Athens"
```

#### 2. Setting Default TimeZone on JAR Execution

Similar to running the bootRun task, we can pass the default TimeZone value in the command line when executing
the JAR file.
And again, the value we set is available from the very beginning of the execution:

```
java -Duser.timezone=Europe/Athens -jar spring-core-4-0.0.1-SNAPSHOT.jar
```

#### 3. Setting Default TimeZone on Spring Boot Startup

##### 3.1. Main method

First, suppose we set the value inside the main method.

In this case, we have it available from the very early stages of the execution,
even before detecting the Spring Profile:

```
@SpringBootApplication
public class MainApplication {

    public static void main(String[] args) {
        TimeZone.setDefault(TimeZone.getTimeZone("GMT+08:00"));

        SpringApplication.run(MainApplication.class, args);
    }
}
```

##### 3.2. BeanFactoryPostProcessor

Second, BeanFactoryPostProcessor is a factory hook that we can use to modify the application context’s bean definitions.

This way, we set the value just before any bean instantiation happens:

```
@Component
public class GlobalTimezoneBeanFactoryPostProcessor implements BeanFactoryPostProcessor {

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        TimeZone.setDefault(TimeZone.getTimeZone("GMT+08:00"));
    }
}
```

##### 3.2. PostConstruct

Last, we can use PostConstruct of our MainApplication class to set the default TimeZone value
just after the initialization of WebApplicationContext is completed.

At this point, we can inject the TimeZone value from our configuration properties:

```
@Configuration
public class TimeZoneConfiguration {

  @Value("${application.timezone:UTC}")
  private String applicationTimeZone;

  /*
   * Last, we can use PostConstruct of our MainApplication class to set the default TimeZone value just after
   * the initialization of WebApplicationContext is completed.
   *
   * At this point, we can inject the TimeZone value from our configuration properties:
   */
  @PostConstruct
  public void init() {
    TimeZone.setDefault(TimeZone.getTimeZone(applicationTimeZone));
  }
}

```