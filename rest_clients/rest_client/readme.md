### RestClient

link: https://www.youtube.com/watch?v=iNWVlF8o0A4&list=PLLMxXO6kMiNiu1wcZovMFWsMw0qGv0KZS&index=5
or
link: https://github.com/nlinhvu/rest-client-demo-2024

RestClient is a synchronous HTTP client introduced in Spring Framework 6.1 M2 that supersedes RestTemplate.
A synchronous HTTP client sends and receives HTTP requests and responses in a blocking manner,
meaning it waits for each request to complete before proceeding to the next one.

link: https://www.baeldung.com/spring-boot-restclient

#### RestClient and RestTemplate

RestTemplate, as the name suggests, is built on a template design pattern.
It’s a behavioral design pattern that defines the skeleton of an algorithm in a method, allowing subclasses
to provide specific implementations for certain steps.
While it’s a powerful pattern, it creates a need for overloading, which can be inconvenient.

To improve on this, RestClient features a fluent API. A fluent API is a design pattern that allows method chaining
in a way that makes the code more readable and expressive by sequentially calling methods on an object,
often without the need for intermediate variables.

#### RestClient in Spring Boot 3 - Builder, Timeout, Interceptor, RequestFactory

##### 1. Practice when using RestClient

`Noted: RestClient is thread-safe`

**~~1.1. Create one instance inside every request-call method~~**

```
@Service
public class HelloServiceClient {

    public String getHelloById() {
        //   <-- 1 instance will be created for every method call
        RestClient restClient = RestClient.create(); 
         ....
        this.restClient.get()...
        ...
    }
    
    public List<String> getHellos() {
        RestClient restClient = RestClient.create();
        this.restClient.get()...
        ...
    }
}
```

**1.2. Create only one instance for a entire service**

```
@Configuration
public class WebConfig {

    @Bean
    public RestClient restClient() {
       return RestClient.create();
    }
}
```

```
@Service
public class HelloServiceClient {
    
    private final RestClient restClient;

    ........
}
```

**1.3. One instance per one ServiceClient Class**

```
@Service
public class HelloServiceClient {
    
    private final RestClient restClient;

    public HelloServiceClient() {
        this.restClient = RestClient.builder()
                .baseUrl("http://helloservice.com")
                .requestInterceptor(new ClientCredentialTokenForHelloServiceInterceptor())
                .build();
    }

    public String getHelloById() {
        this.restClient.get()...
        ...
    }


    public List<String> getHellos() {
        this.restClient.get()...
        ...
    }
}
```

````
@Service
public class HiServiceClient {
    
    private final RestClient restClient;

    public HiServiceClient() {
        this.restClient = RestClient.builder()
                .baseUrl("http://hiservice.com")
                .requestInterceptor(new ClientCredentialTokenForHiServiceInterceptor())
                .build();
    }

    public String getHiById() {
        this.restClient.get()...
        ...
    }


    public List<String> getHis() {
        this.restClient.get()...
        ...
    }
}
````

**2. Create a RestClient**


**2.1. In Spring Framework, from static methods of RestClient: create, builder**

```
RestClient defaultClient = RestClient.create();

RestClient customClient = RestClient.builder()
        .requestFactory(new HttpComponentsClientHttpRequestFactory())
        .messageConverters(converters -> converters.add(new MyCustomMessageConverter()))
        .baseUrl("https://example.com")
        .defaultUriVariables(Map.of("variable", "foo"))
        .defaultHeader("My-Header", "Foo")
        .requestInterceptor(myCustomInterceptor)
        .requestInitializer(myCustomInitializer)
        .build();
```

**2.2. Spring Boot adds 1 additional way is from auto-configured RestClient.Builder 
prototype bean in RestClientAutoConfiguration class**

file: spring-autoconfigure-metadata.properties

```
org.springframework.boot.autoconfigure.elasticsearch.ElasticsearchRestClientAutoConfiguration.ConditionalOnClass=org.elasticsearch.client.RestClientBuilder
```

RestClientAutoConfiguration {@link org.springframework.boot.autoconfigure.web.client.RestClientAutoConfiguration}

```
@AutoConfiguration(
    after = {HttpMessageConvertersAutoConfiguration.class, SslAutoConfiguration.class}
)
@ConditionalOnClass({RestClient.class})
@Conditional({NotReactiveWebApplicationCondition.class})
public class RestClientAutoConfiguration {
    
    public RestClientAutoConfiguration() {
    }
    
    .......
    
    @Bean
    @Scope("prototype")
    @ConditionalOnMissingBean
    RestClient.Builder restClientBuilder(RestClientBuilderConfigurer restClientBuilderConfigurer) {
        RestClient.Builder builder = RestClient.builder().requestFactory(ClientHttpRequestFactories.get(ClientHttpRequestFactorySettings.DEFAULTS));
        return restClientBuilderConfigurer.configure(builder);
    }
```

3. Set Connection Timeout (connectTimeout), Response Timeout (readTimeout), and RequestFactory