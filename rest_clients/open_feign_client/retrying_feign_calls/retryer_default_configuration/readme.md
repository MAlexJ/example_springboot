### Feign Retry Mechanism Spring Boot

Utilizing REST endpoints to communicate with external services is a routine task, streamlined by frameworks such as
Feign.
Nevertheless, encountering errors during these interactions is not uncommon,
as various issues, often transient or unpredictable, may arise.

In this guide, we will explore strategies to handle failed requests effectively,
enhancing the resilience of our REST clients through retry mechanisms.

To retry Feign calls in Spring Boot, you can utilize the Retryer interface provided by Feign along
with Spring’s configuration capabilities.

link: https://learnspringbootonline.com/blog/feign-retry-mechanism-spring-boot/

### Retrying Feign Calls

Calling external services through the REST endpoint is a common activity that was made very straightforward 
by libraries like Feign. 
However, a lot of things can go wrong during such calls. 
Many of these problems are random or temporary.

In this tutorial, we’ll learn how to retry failed calls and make more resilient REST clients.

link: https://www.baeldung.com/feign-retry#1-errordecoder-and-retryableexception

### ErrorDecoder and RetryableException

When we receive an erroneous response, Feign passes it to an instance of the ErrorDecoder interface 
that decides what to do with it. 
Most importantly, the decoder can map an exception to an instance of RetryableException, 
enabling Retryer to retry the call. 

The default implementation of ErrorDecoder only creates a RetryableExeception instance 
when the response contains the “Retry-After” header. 
Most commonly, we can find it in 503 Service Unavailable responses.

That’s good default behavior, but sometimes we need to be more flexible. 
For example, we could be communicating with an external service that, 
from time to time, randomly responds with 500 Internal Server Error, 
and we have no power to fix it. 

What we can do is to retry the call because we know that it’ll probably work next time. 
To achieve that, we’ll need to write a custom ErrorDecoder implementation.


link: https://www.baeldung.com/feign-retry#1-errordecoder-and-retryableexception

#### Create Feign Client Interface

Define your Feign client interface as you would normally do.

```
@FeignClient(name = "openFeignClient", url = "${service.client.url}")
public interface OpenFeignClient {

  @GetMapping("/${service.client.path.status}")
  String info(@PathVariable Integer status);
}

```

#### Configure Retry

Configure the retry mechanism for Feign. You can create a configuration class to define the retry behavior

```
@Slf4j
@Configuration
public class OpenFeignRetryer {

  /*
   * Retry 3 times with 100ms initial interval and 1000ms max interval
   */
  @Bean
  public Retryer retryer() {
    return new Retryer.Default(100, 1000, 3);
  }
}

```

#### Configure error decoder

```
@Slf4j
@Configuration
public class FeignErrorDecoder implements ErrorDecoder {

  private static final long RETRY_AFTER = 10000;

  private final ErrorDecoder defaultErrorDecoder = new Default();

  @Override
  public Exception decode(String methodKey, Response response) {
    var exception = defaultErrorDecoder.decode(methodKey, response);
    var status = response.status();
    var request = response.request();
    var httpMethod = request.httpMethod();
    var err = exception.getMessage();
    log.error("******************** START OF RETRY");
    log.error("**** Feign status -{}", status);
    log.error("**** Feign methodKey - {}", methodKey);
    return new RetryableException(status, err, httpMethod, exception, RETRY_AFTER, request);
  }
}
```

### Enable log level

Add log level properties to yaml file

```
spring:
  cloud:
    openfeign:
      client:
        config:
          openFeignClient:
            connectTimeout: 2000
            readTimeout: 2000
            loggerLevel: full

logging:
  level:
    com.malex.webservice: DEBUG
```