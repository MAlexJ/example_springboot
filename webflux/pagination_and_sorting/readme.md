### Pagination and sort

#### Pagination in Spring Webflux and Spring Data Reactive

pom.xml

```
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-webflux</artifactId>
</dependency>
```

link: https://www.baeldung.com/spring-data-webflux-pagination

Pagination is an essential concept when dealing with endpoints that return large collections of resources.
It allows for efficient retrieval and presentation of data by breaking it down into smaller, manageable chunks called
“pages”.

note:
Implementing a pagination system can significantly enhance the user experience. Rather than fetching the entire set of
records at once, it would be more effective to retrieve a few records initially and provide an option to load the next
set of records upon request.

Spring Data Reactive still supports Pageable. We can configure it with a PageRequest object to retrieve specific chunks
of data and add an explicit query to fetch the total count of records.

We can get a Flux of responses as opposed to Page when using Spring Data which contains metadata about the records on
the page.

#### Spring WebFlux and Pageable

hen working with Spring WebFlux, one of the issues that might arise is the use of the Pageable interface, a tool
commonly used to handle data pagination. However, developers often encounter the following error when trying to
implement it:

exception:

```
java.lang.IllegalStateException: No primary or single unique constructor found for interface org.springframework.data.domain.Pageable
```

This error occurs because Spring WebFlux attempts to instantiate the Pageable interface. As Pageable is an interface, it
cannot be instantiated directly. Attempting to do so results in an IllegalStateException.

#### Reactive Pagination with Sorting and Filtering in Spring Boot with PostgreSQL and Tests

link: https://medium.com/@mbanaee61/reactive-pagination-with-sorting-and-filtering-in-spring-boot-with-postgresql-61036a8d32fe

### ResponseEntity is like @ResponseBody but with status and headers

link:

WebFlux supports using a single value reactive type to produce the ResponseEntity asynchronously, and/or single and
multi-value reactive types for the body.

```
 *   ResponseEntity<Mono<T>> or ResponseEntity<Flux<T>> make the response status and headers
        known immediately while the body is provided asynchronously at a later point. Use Mono if
        the body consists of 0..1 values or Flux if it can produce multiple values.
        
 *   Mono<ResponseEntity<T>> provides all three: response status, headers, and body,
        asynchronously at a later point. This allows the response status and headers to vary
        depending on the outcome of asynchronous request handling.
        
 *  Mono<ResponseEntity<Mono<T>>> or Mono<ResponseEntity<Flux<T>>> are yet another possible,
        albeit less common alternative. They provide the response status and headers asynchronously
        first and then the response body, also asynchronously, second.
```


