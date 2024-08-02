package com.malex.repository;

import com.malex.exception.DuplicateKeyException;
import com.malex.exception.NotFoundException;
import com.malex.model.dto.UpdateUserRequest;
import com.malex.model.dto.UserResponse;
import com.malex.model.entity.UserEntity;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class CrudRepository {

  private static final AtomicInteger COUNTER = new AtomicInteger(1);

  private final Map<Integer, UserEntity> inMemoryDatabase;

  /*
   * Find all users
   *
   * return list of user response, otherwise empty list
   */
  public Flux<UserResponse> findAll() {
    return Flux.fromStream(
            inMemoryDatabase.entrySet().stream()
                .map(
                    entry -> {
                      var id = entry.getKey();
                      var name = entry.getValue().name();
                      return new UserResponse(id, name);
                    }))
        .flatMap(Flux::just);
  }

  /*
   * Find user by id
   *
   * return: user response with id if the user was found, otherwise null
   */
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

  /*
   * Save user
   *
   * return saved user or throw the duplicate key exception
   */
  public Mono<UserResponse> save(UserEntity user) {
    var key = COUNTER.getAndIncrement();
    var name =
        inMemoryDatabase
            .compute(
                key,
                (k, v) -> {
                  if (Objects.nonNull(v)) {
                    throw new DuplicateKeyException("Duplicate key");
                  }
                  return user;
                })
            .name();
    return Mono.just(new UserResponse(key, name));
  }

  /*
   * Update user
   *
   * return updated user or throw not found key exception
   */
  public Mono<UserResponse> update(UpdateUserRequest userRequest) {
    var key = userRequest.id();
    var name = userRequest.name();
    Optional.ofNullable(inMemoryDatabase.computeIfPresent(key, (k, v) -> new UserEntity(name)))
        .orElseThrow(() -> new NotFoundException("User not found by id: " + key));
    return Mono.just(new UserResponse(key, name));
  }

  /*
   * Delete user by id
   *
   * return: user id if the user was found, otherwise null
   */
  public Mono<Integer> deleteById(Integer id) {
    return Optional.ofNullable(inMemoryDatabase.remove(id))
        .map(entity -> Mono.just(id))
        .orElse(Mono.empty());
  }
}
