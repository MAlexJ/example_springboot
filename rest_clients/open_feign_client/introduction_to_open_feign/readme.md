### Feign Client

Feign — a declarative HTTP client developed by Netflix.

Feign aims at simplifying HTTP API clients. Simply put, the developer needs only to declare
and annotate an interface while the actual implementation is provisioned at runtime.

link: https://github.com/OpenFeign/feign

#### 1. Add dependency to the project

gradle:

```
ext {
    set('springCloudVersion', "${springCloudCDependencyVersion}")
}

dependencies {
.....
implementation 'org.springframework.cloud:spring-cloud-starter-openfeign'
....
}

dependencyManagement {
    imports {
        mavenBom "org.springframework.cloud:spring-cloud-dependencies:${springCloudVersion}"
    }
}
```

#### 2. enable fein client with annotation

First need to add @EnableFeignClients to our main class:

```
@SpringBootApplication
@EnableFeignClients
public class ExampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExampleApplication.class, args);
    }
}
```

#### 3. Create feign interface

Then we declare a Feign client using the @FeignClient annotation:

```
@FeignClient(value = "jplaceholder", url = "https://jsonplaceholder.typicode.com/")
public interface JSONPlaceHolderClient {

    @RequestMapping(method = RequestMethod.GET, value = "/posts")
    List<Post> getPosts();

    @RequestMapping(method = RequestMethod.GET, value = "/posts/{postId}", produces = "application/json")
    Post getPostById(@PathVariable("postId") Long postId);
}
```

#### 4. Configuration

Now, it’s very important to understand that each Feign client is composed of a set of customizable components.

Spring Cloud creates a new default set on demand for each named client using the FeignClientsConfiguration class
that we can customize as explained in the next section.

The above class contains these beans:

* Decoder – ResponseEntityDecoder, which wraps SpringDecoder, used to decode the Response
* Encoder – SpringEncoder is used to encode the RequestBody.
* Logger – Slf4jLogger is the default logger used by Feign.
* Contract – SpringMvcContract, which provides annotation processing
* Feign-Builder – HystrixFeign.Builder is used to construct the components.
* Client – LoadBalancerFeignClient or default Feign client

