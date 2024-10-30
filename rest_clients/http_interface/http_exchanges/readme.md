### HTTP Interface in Spring /declarative HTTP services using Java interfaces

The Spring Framework release 6, as well as Spring Boot version 3, enables us to define declarative HTTP services
using Java interfaces.
The approach is inspired by popular HTTP client libraries like Feign and is similar to how we define repositories in
Spring Data.

link: https://www.baeldung.com/spring-6-http-interface

#### HTTP Interface

The declarative HTTP interface includes annotated methods for HTTP exchanges.
We can simply express the remote API details using an annotated Java interface
and let Spring generate a proxy that implements this interface and performs the exchanges.
This helps reduce the boilerplate code.

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

#### Exchange Methods

@HttpExchange is the root annotation we can apply to an HTTP interface and its exchange methods.
In case we apply it on the interface level, then it applies to all exchange methods.
This can be useful for specifying attributes common to all interface methods like content type or URL prefix.

Additional annotations for all the HTTP methods are available:

* @GetExchange for HTTP GET requests
* @PostExchange for HTTP POST requests
* @PutExchange for HTTP PUT requests
* @PatchExchange for HTTP PATCH requests
* @DeleteExchange for HTTP DELETE requests

```
interface BooksService {

    @GetExchange("/books")
    List<Book> getBooks();

    @GetExchange("/books/{id}")
    Book getBook(@PathVariable long id);

    @PostExchange("/books")
    Book saveBook(@RequestBody Book book);

    @DeleteExchange("/books/{id}")
    ResponseEntity<Void> deleteBook(@PathVariable long id);
}
```

We should note that all the HTTP method-specific annotations are meta-annotated with `@HttpExchange`.

Therefore, `@GetExchange(“/books”)` is equivalent to `@HttpExchange(url = “/books”, method = “GET”)`.

#### Method Parameters

In our example interface, we used @PathVariable and @RequestBody annotations for method parameters.

In addition, we may use the following set of method parameters for our exchange methods:

* URI: dynamically sets the URL for the request, overriding the annotation attribute
* HttpMethod: dynamically sets the HTTP method for the request, overriding the annotation attribute
* @RequestHeader: adds the request header names and values, the argument may be a Map or MultiValueMap
* @PathVariable: replaces a value that has a placeholder in the request URL
* @RequestBody: provides the body of the request either as an object to be serialized,
  or a reactive streams publisher such as Mono or Flux
* @RequestParam: adds request parameter names and values, the argument may be a Map or MultiValueMap
* @CookieValue: adds cookie names and values, the argument may be a Map or MultiValueMap

We should note that request parameters are encoded in the request body only
for content type “application/x-www-form-urlencoded”.
Otherwise, request parameters are added as URL query parameters.

#### Return Values

In our example interface, the exchange methods return blocking values.
However, declarative HTTP interface exchange methods support both `blocking` and `reactive` return values.

In addition, we may choose to return only the specific response information, such as status codes or headers.
As well as returning void in case we are not interested in the service response at all.

To summarize, HTTP interface exchange methods support the following set of return values:

* void, Mono<Void>: performs the request and releases the response content
* HttpHeaders, Mono<HttpHeaders>: performs the request, releases the response content, and returns the response headers
* <T>, Mono<T>: performs the request and decodes the response content to the declared type
* <T>, Flux<T>: performs the request and decodes the response content to a stream of the declared type
* ResponseEntity<Void>, Mono<ResponseEntity<Void>>: performs the request, releases the response content,
  and returns a ResponseEntity containing status and headers
* ResponseEntity<T>, Mono<ResponseEntity<T>>: performs the request, releases the response content,
  and returns a ResponseEntity containing status, headers, and the decoded body
* Mono<ResponseEntity<Flux<T>>: performs the request, releases the response content,
  and returns a ResponseEntity containing status, headers, and the decoded response body stream

We can also use any other `async` or `reactive` types registered in the ReactiveAdapterRegistry.