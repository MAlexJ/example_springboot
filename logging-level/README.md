## Spring app login level

Logger:
An object that records log messages in the software application.
Loggers are typically associated with specific classes or packages and are used to categorize and manage log messages.

Log Level:
Indicates the severity or importance of the log message. Common log levels include:

DEBUG: Shows detailed information for debugging purposes.
INFO: Shows informational messages highlighting the progress of the application.
WARN: Shows warnings indicating potential issues or unexpected behavior.
ERROR: Indicates error messages for failures or problems requiring attention.
TRACE: Provides very detailed information, more granular than DEBUG, often used for tracing program execution.

link: https://www.baeldung.com/spring-boot-logging

That’s because every starter, like our spring-boot-starter-web, depends on spring-boot-starter-logging, which already
pulls in spring-jcl for us.

### Default Logback Logging

When using starters, Logback is used for logging by default.

##### we can pass the –debug or –trace arguments on the command line:

```
java -jar target/spring-boot-logging-0.0.1-SNAPSHOT.jar --trace
```

### Log Levels

Spring Boot also gives us access to a more fine-grained log level setting via environment variables

##### VM Options:

```
-Dlogging.level.org.springframework=TRACE 
-Dlogging.level.com.baeldung=TRACE
```

If we want to change the verbosity permanently, we can do so in the application.properties file as described

##### application.properties / yaml

```
logging.level.root=WARN
logging.level.com.baeldung=TRACE
```

### Logback Configuration Logging

When a file in the classpath has one of the following names, Spring Boot will automatically load it over the default
configuration:

```
logback-spring.xml
logback.xml
logback-spring.groovy
logback.groovy
```

#### Open the application file and write the below code in the project for server port and logging configuration

```
spring.application.name=spring-log-demo

server.port=8080

# Set logging level for the root logger
logging.level.root=INFO

# Set logging level for a specific package
logging.level.org.example.springlogdemo.LogDemo.LogExample=DEBUG
```
