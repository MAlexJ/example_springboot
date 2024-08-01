### Functional Endpoints

Spring WebFlux includes WebFlux.fn, a lightweight functional programming model in which functions are used to route and
handle requests and contracts are designed for immutability.

link: https://docs.spring.io/spring-framework/reference/web/webflux-functional.html

### Difference between @Controller and RouterFunction in Spring 5 WebFlux

link: https://stackoverflow.com/questions/47092029/difference-between-controller-and-routerfunction-in-spring-5-webflux

There are two ways to expose HTTP endpoints in spring 5 now.

1. @Controller or @RestController by making the controller's class, e.g.

```java

@RestController
@RequestMapping("persons")
public class PersonController {

    @Autowired
    private PersonRepositorty repository;

    @GetMapping("/{id}")
    public Mono<Person> personById(@PathVariable String id) {
        retrun repository.findById(id);
    }
}

```

2. Route in @Configuration class by using RouterFunctions:

```java

@Bean
public RouterFunction<ServerResponse> personRoute(PersonRepo repo) {
    return route(GET("/persons/{id}"), req -> Mono.justOrEmpty(req.pathVariable("id"))
            .flatMap(repo::getById)
            .flatMap(p -> ok().syncBody(p))
            .switchIfEmpty(notFound().build()));
}
```
