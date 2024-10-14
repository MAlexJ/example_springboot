### Configuration Using Properties

link: https://www.baeldung.com/spring-cloud-openfeign#properties

Rather than use a Configuration class, we can use application properties to configure Feign clients,
as shown in this application.yaml example:

```
feign:
  client:
    config:
      default:
        connectTimeout: 5000
        readTimeout: 5000
        loggerLevel: basic
```

With this configuration, we’re setting the timeouts to five seconds
and the logger level to basic for each declared client in the application.

Finally, we can create the configuration with default as the client name to configure all @FeignClient objects,
or we can declare the feign client name for a configuration:

```
feign:
  client:
    config:
      jplaceholder:
```

If we have both Configuration bean and configuration properties,
configuration properties will override Configuration bean values.