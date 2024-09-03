package com.malex.function_endpoints.endpoints;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

import java.util.Objects;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class PersonRestEndpoints {

  private final PersonHandler handler;

  @Bean
  public RouterFunction<ServerResponse> routes() {
    return route()
        .GET("/person/{id}", accept(APPLICATION_JSON), handler::getPerson)
        .GET("/person", accept(APPLICATION_JSON), handler::listPeople)
        .POST("/person", handler::createPerson)
        .build();
  }

  @Component
  static class PersonHandler {

    public Mono<ServerResponse> getPerson(ServerRequest request) {
      String id = request.pathVariable("id");
      return ServerResponse.ok()
          .contentType(APPLICATION_JSON)
          .body(Mono.just(new Person("Random")), Person.class);
    }

    public Mono<ServerResponse> listPeople(ServerRequest request) {
      return ServerResponse.ok()
          .body(
              Flux.fromStream(Stream.of(new Person("Alex"), new Person("Max"))).flatMap(Flux::just),
              Person.class);
    }

    public Mono<ServerResponse> createPerson(ServerRequest request) {
      return ServerResponse.ok()
          .body(request.bodyToMono(Person.class).map(Objects::requireNonNull), Person.class);
    }
  }

  record Person(String name) {}
}
