### Feign Spring Cloud CircuitBreaker Fallbacks

With the fallback pattern, when a remote service call fails, rather than generating an exception,
the service consumer will execute an alternative code path to try to carry out the action through another means.

link: https://docs.spring.io/spring-cloud-openfeign/docs/current/reference/html/#spring-cloud-feign-circuitbreaker-fallback

#### Configuration

Spring Cloud CircuitBreaker supports the notion of a fallback: 
a default code path that is executed when the circuit is open or there is an error. 

To enable fallbacks for a given @FeignClient set the fallback attribute to the class name that implements the fallback. 
You also need to declare your implementation as a Spring bean.

##### 1. Enable feign circuitbreaker

Add support circuitbreaker in application.yaml file

```
spring:
  application:
    name: circuitbreaker_fallback_pattern

  cloud:
    openfeign:
      circuitbreaker:
        enabled: true
        alphanumeric-ids:
          enabled: true
```

#### 2. Add dependency in build gradle

```
dependencies {
    ...
    implementation "org.springframework.cloud:spring-cloud-starter-circuitbreaker-resilience4j"
    ...
    }
```

#### 3. impl Feign interface

```
@Slf4j
@Component
public class OpenFeignFallbackService implements OpenFeignClientImpl {

  @Override
  public String findStatus(@PathVariable Integer status) {
    log.info(">>> FallbackService findStatus");
    return "Fallback";
  }
}
```

#### 4. Add fallback class to feign client

```
@FeignClient(
    name = "openFeignClientImpl",
    url = "${service.client.url}",
    fallback = OpenFeignFallbackService.class)
public interface OpenFeignClientImpl {

  @GetMapping("/${service.client.path.status}")
  String findStatus(@PathVariable Integer status);
}
```

