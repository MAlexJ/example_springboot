package com.malex.handler;

import com.malex.model.dto.UpdateUserRequest;
import com.malex.model.dto.UserResponse;
import com.malex.model.entity.UserEntity;
import com.malex.repository.CrudRepository;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

/*
 * RouterFunction
 *
 * link: https://hantsy.github.io/spring-reactive-sample/web/func.html
 */
@Component
@RequiredArgsConstructor
public class RestHandler {

  private final CrudRepository repository;

  public Mono<ServerResponse> all(ServerRequest request) {
    return ServerResponse.ok().body(this.repository.findAll(), UserResponse.class);
  }

  public Mono<ServerResponse> create(ServerRequest request) {
    return request
        .bodyToMono(UserEntity.class)
        .flatMap(repository::save)
        .flatMap(user -> ServerResponse.created(URI.create("/users/" + user.id())).build());
  }

  public Mono<ServerResponse> get(ServerRequest request) {
    String id = request.pathVariable("id");
    return repository
        .findById(Integer.parseInt(id))
        .flatMap(user -> ServerResponse.ok().body(Mono.just(user), UserResponse.class))
        .switchIfEmpty(ServerResponse.notFound().build());
  }

  public Mono<ServerResponse> update(ServerRequest request) {
    var id = request.pathVariable("id");
    return Mono.zip(
            // result - combine function
            (data) -> {
              UserResponse userResponse = (UserResponse) data[0];
              UserEntity user = (UserEntity) data[1];
              return new UpdateUserRequest(userResponse.id(), user.name());
            },
            // data[0]- get exist user from database
            repository.findById(Integer.parseInt(id)),
            // data[1] - get user from request
            request.bodyToMono(UserEntity.class))
        .cast(UpdateUserRequest.class)
        .flatMap(repository::update)
        .flatMap(user -> ServerResponse.created(URI.create("/users/" + user.id())).build())
        .switchIfEmpty(ServerResponse.notFound().build());
  }

  public Mono<ServerResponse> delete(ServerRequest request) {
    String id = request.pathVariable("id");
    return repository
        .deleteById(Integer.parseInt(id))
        .flatMap(entityId -> ServerResponse.noContent().build())
        .switchIfEmpty(ServerResponse.notFound().build());
  }
}
