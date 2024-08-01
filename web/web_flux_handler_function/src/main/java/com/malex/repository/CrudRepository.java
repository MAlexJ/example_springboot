package com.malex.repository;

import com.malex.dto.UserResponse;
import com.malex.model.User;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class CrudRepository {

  private final Map<Integer, User> inMemoryDatabase;

  public Flux<UserResponse> findAll() {
    var users =
        inMemoryDatabase.entrySet().stream()
            .map(
                entry -> {
                  var id = entry.getKey();
                  var name = entry.getValue().name();
                  return new UserResponse(id, name);
                })
            .toList();
    return Flux.fromIterable(users).flatMap(Flux::just);
  }

  public Mono<UserResponse> findById(Integer id) {
    return Optional.ofNullable(inMemoryDatabase.get(id))
        .map(
            user -> {
              var name = user.name();
              return new UserResponse(id, name);
            })
        .map(Mono::just)
        .orElse(Mono.empty());
  }

  public Mono<UserResponse> save(User user) {
    UserResponse response =
        inMemoryDatabase.keySet().stream()
            .max(Integer::compareTo)
            .map(
                id -> {
                  var nextId = id + 1;
                  var name = inMemoryDatabase.computeIfAbsent(nextId, key -> user).name();
                  return new UserResponse(nextId, name);
                })
            .or(
                () -> {
                  var id = 1;
                  var name = inMemoryDatabase.computeIfAbsent(id, key -> user).name();
                  return Optional.of(new UserResponse(id, name));
                })
            .orElseThrow();
    return Mono.just(response);
  }

  public Mono<Void> deleteById(Integer id) {
    return Optional.ofNullable(inMemoryDatabase.remove(id))
        .map(user -> Mono.empty().then())
        .orElse(Mono.empty());
  }
}
