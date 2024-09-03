## Reactive Streams

Reactive Streams is an initiative to provide a standard for asynchronous stream processing
with non-blocking back pressure.
This encompasses efforts aimed at runtime environments (JVM and JavaScript) as well as network protocols.

### Functional Endpoints

In WebFlux.fn, an HTTP request is handled with a HandlerFunction: a function that takes ServerRequest and returns a
delayed ServerResponse

link: https://hantsy.github.io/spring-reactive-sample/web/func.html

link: https://docs.spring.io/spring-framework/reference/web/webflux-functional.html

#### Functional Endpoints JavaRush

link: https://javarush.com/quests/lectures/questspring.level05.lecture03

#### Spring-Webflux: Testing your Router Functions with WebTestClient

WebTestClient and WebFluxTest

I recently started working on the functional approach of Spring Boot Webflux. You can explore it more on my previous
blog on Spring Boot Webflux. It is a new concept and you may not find many useful blogs on it unlike for annotation
based controllers. However, going with some trial and error, I have come out with how one can test its router functions
along with its handler. We will we using WebTestClient and WebFluxTest here to do the testing.

link: https://blog.knoldus.com/spring-webflux-testing-your-router-functions-with-webtestclient/

Spring Boot Unit Testing Functional Endpoints and Service with API calls using Spring WebFlux

link: https://jskim1991.medium.com/spring-boot-unit-testing-with-spring-webflux-b954b357c17

### Handling Errors in Spring WebFlux

link: https://www.baeldung.com/spring-webflux-errors

1. Handling Errors at a Functional Level

- Handling Errors With onErrorReturn
- Handling Errors With onErrorResume

2. Handling Errors at a Global Level
   Simply extend the DefaultErrorAttributes class and override its getErrorAttributes() method

### Spring Web And WebFlux Exception Handling Best Practices

link: https://medium.com/codex/spring-web-and-webflux-exception-handling-best-practices-b2c3cd7e3acf