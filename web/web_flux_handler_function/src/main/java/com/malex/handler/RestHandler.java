package com.malex.handler;

import com.malex.dto.UserResponse;
import com.malex.model.User;
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
    return ServerResponse.ok().body(this.repository.findAll(), User.class);
  }

  public Mono<ServerResponse> create(ServerRequest request) {
    return request
        .bodyToMono(User.class)
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

  public Mono<ServerResponse> update(ServerRequest serverRequest) {
    //    return Mono
    //            .zip(
    //                    (data) -> {
    //                      Post p = (Post) data[0];
    //                      Post p2 = (Post) data[1];
    //                      p.setTitle(p2.getTitle());
    //                      p.setContent(p2.getContent());
    //                      return p;
    //                    },
    //                    this.posts.findById(req.pathVariable("id")),
    //                    req.bodyToMono(Post.class)
    //            )
    //            .cast(Post.class)
    //            .flatMap(post -> this.posts.save(post))
    //            .flatMap(post -> ServerResponse.noContent().build());
    //
    return null;
  }

  public Mono<ServerResponse> delete(ServerRequest request) {
    String id = request.pathVariable("id");
    return ServerResponse.noContent().build(repository.deleteById(Integer.parseInt(id)));
  }
}
