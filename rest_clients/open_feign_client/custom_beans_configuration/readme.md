### Feign Client

#### Custom Beans Configuration

link: https://www.baeldung.com/spring-cloud-openfeign#configuration

### 1. Add new dependencies

In this example, we tell Feign to use OkHttpClient instead of the default one to support HTTP/2.

Feign supports multiple clients for different use cases, including the ApacheHttpClient, which sends more headers with
the request, for example, Content-Length, which some servers expect

We can find the latest versions of feign-okhttp and feign-httpclient on Maven Central.

To use these clients, let’s not forget to add the required dependencies to our pom.xml file:

maven:

```
<dependency>
<groupId>io.github.openfeign</groupId>
<artifactId>feign-okhttp</artifactId>
</dependency>

<dependency>
    <groupId>io.github.openfeign</groupId>
    <artifactId>feign-httpclient</artifactId>
</dependency>
```

gradle:

```
    implementation "io.github.openfeign:feign-okhttp:${openfeignVersion}"
    implementation "io.github.openfeign:feign-httpclient:${openfeignVersion}"
```

#### 2. Add Feign configuration class

If we want to customize one or more of these beans, we can override them by creating a Configuration class,
which we then add to the FeignClient annotation:

```
public class ClientConfiguration {

    @Bean
    public OkHttpClient client() {
        return new OkHttpClient();
    }
}
```

#### 3. Add config class to Feign interface

```
@FeignClient(value = "jplaceholder", 
            url = "https://jsonplaceholder.typicode.com/",
            configuration = ClientConfiguration.class)
public interface OpenFeignWebclient {
   
   @GetMapping(value = "/posts/{postId}", produces = "application/json")
   Post getPostById(@PathVariable("postId") Long postId);

   .........
   
}  
```
