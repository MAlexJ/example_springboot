### HTTP Interface in Spring

By default, WebClient throws WebClientResponseException for any client or server error HTTP status codes.
We can customize exception handling by registering a default response status handler that applies to all responses
performed through the client:

Class:

```
BooksClient booksClient = new BooksClient(WebClient.builder()
   // handle  exception
  .defaultStatusHandler(HttpStatusCode::isError, resp ->Mono.just(new MyServiceException("Custom exception")))
  .baseUrl(serviceUrl)
  .build());
```

configuration bean:

```
@Slf4j
@Configuration
public class WebHttpExchangeConfig {

  @Value("${webservice.client.url}")
  private String httpBinUrl;

  @Bean
  public WebClient httpBinWebClient() {
    return WebClient.builder()
        .baseUrl(httpBinUrl)
        
        // handle 5xx error
        .defaultStatusHandler(
            httpStatusCode -> {
              boolean httpStatusCode2xxSuccessful = httpStatusCode.is2xxSuccessful();
              if (httpStatusCode2xxSuccessful) {
                log.info("Http status code - {}", httpStatusCode);
              }
              boolean error = httpStatusCode.isError();
              boolean httpStatusCode4xxClientError = httpStatusCode.is4xxClientError();

              boolean httpStatusCode5xxServerError = httpStatusCode.is5xxServerError();
              if (error) {
                log.error(
                    "Http error status code - {} , is 4xx - {} or is 5xx - {}",
                    httpStatusCode,
                    httpStatusCode4xxClientError,
                    httpStatusCode5xxServerError);
              }
              return httpStatusCode5xxServerError;
            },
            clientResponse -> Mono.just(new ServerNotAvailableException("Custom exception")))
            
         // handle 4xx error   
        .defaultStatusHandler(
            HttpStatusCode::is4xxClientError,
            clientResponse ->
                Mono.error(
                    new ResponseStatusException(
                        clientResponse.statusCode(), clientResponse.toString())))
        .build();[WebHttpExchangeConfig.java](src/main/java/com/malex/exception_handling/configuration/WebHttpExchangeConfig.java)
  }

  @Bean
  public WebHttpExchangeInterface webHttpExchangeInterface(WebClient httpBinWebClient) {
    var webClientAdapter = WebClientAdapter.create(httpBinWebClient);
    var httpServiceProxyFactory = HttpServiceProxyFactory.builderFor(webClientAdapter).build();
    return httpServiceProxyFactory.createClient(WebHttpExchangeInterface.class);
  }
}
```

link: https://www.baeldung.com/spring-6-http-interface

#### Configuration

##### 1. Add additional dependencies

```
dependencies {
    ...
    implementation 'org.springframework.boot:spring-boot-starter-webflux'
    ...
}

```

##### 2. Create HttpExchange interface

```
public interface PostExchangeInterface {

  @GetExchange("/posts")
  List<HttpExchangesApplication.Post> findAll();
}
```

##### 3.Create HttpService Proxy configuration

```
@Configuration
public class HttpClientProxyConfig {

  @Value("${webservice.client.url}")
  private String jsonPlaceholderURL;

  @Bean
  public WebClient jsonPlaceholderWebClient() {
    return WebClient.builder().baseUrl(jsonPlaceholderURL).build();
  }

  @Bean
  public PostExchangeInterface postsClient(WebClient jsonplaceholderWebClient) {
    var webClientAdapter = WebClientAdapter.create(jsonplaceholderWebClient);
    var httpServiceProxyFactory = HttpServiceProxyFactory.builderFor(webClientAdapter).build();
    return httpServiceProxyFactory.createClient(PostExchangeInterface.class);
  }
}
```