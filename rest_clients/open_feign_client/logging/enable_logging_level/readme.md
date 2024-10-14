### Logging

link: https://www.baeldung.com/spring-cloud-openfeign#logging

For each Feign client, a logger is created by default.

To enable logging, we should declare it in the application.yaml file
using the package name of the client interfaces:

```
logging:
  level:
    com.malex.logging.openfeign.client: DEBUG
```

Or, if we want to enable logging only for one particular client in a package,
we can use the full class name:

```
logging:
  level:
    com.malex.enable_logging_level.OpenFeignWebclient: DEBUG
    // OpenFeignWebclient - the_name_of_your_feign_client 
```

Note that Feign logging responds only to the DEBUG level.

The Logger.Level that we may configure per client indicates how much to log:

```
public class ClientConfiguration {
    
    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.BASIC;
    }
    
}
```

There are four logging levels to choose from:

* NONE – no logging, which is the default
* BASIC – log only the request method, URL and response status
* HEADERS – log the basic information together with request and response headers
* FULL – log the body, headers and metadata for both request and response

application configuration;

```
feign:
  client:
    config:
      the_name_of_your_feign_client:
        logger-level: FULL
```

or default config:

```
spring:
  cloud:
    openfeign:
      client:
        config:
          default:
            connectTimeout: 5000
            readTimeout: 5000
            loggerLevel: basic
```