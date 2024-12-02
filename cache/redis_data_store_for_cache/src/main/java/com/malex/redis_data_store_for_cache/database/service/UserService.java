package com.malex.redis_data_store_for_cache.database.service;

import com.malex.redis_data_store_for_cache.cache.service.UserCacheService;
import com.malex.redis_data_store_for_cache.database.entity.UserEntity;
import com.malex.redis_data_store_for_cache.rest.request.CreateUserRequest;
import com.malex.redis_data_store_for_cache.rest.request.UpdateUserRequest;
import com.malex.redis_data_store_for_cache.rest.response.CreateUserResponse;
import com.malex.redis_data_store_for_cache.rest.response.DeleteUserResponse;
import com.malex.redis_data_store_for_cache.rest.response.FindUserByIdResponse;
import com.malex.redis_data_store_for_cache.rest.response.FindUsersResponse;
import com.malex.redis_data_store_for_cache.rest.response.UpdateUserResponse;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Function;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  private final AtomicLong generatePrimaryKey;

  private final UserCacheService cacheService;

  public UserService(
      @Qualifier("inMemoryPrimaryKeyGenerator") AtomicLong generatePrimaryKey,
      UserCacheService cacheService) {
    this.generatePrimaryKey = generatePrimaryKey;
    this.cacheService = cacheService;
  }

  public FindUsersResponse findAll() {
      var entities = cacheService.findAll().stream().map(this::mapToResponse).toList();
      return new FindUsersResponse (entities, entities.size());
  }

  public Optional<FindUserByIdResponse> findById(Long id) {
    return cacheService.findById(id).map(this::mapToResponse);
  }

  public CreateUserResponse save(CreateUserRequest userRequest) {
    var entity = mapToEntity(generatePrimaryKey.incrementAndGet(), userRequest);
    return Optional.ofNullable(cacheService.save(entity))
        .map(
            persistEntity -> {
              var id = persistEntity.id();
              var username = persistEntity.username();
              var created = persistEntity.created();
              return new CreateUserResponse(id, username, created);
            })
        .orElseThrow(
            () -> new IllegalArgumentException("Cannot save user - %s".formatted(userRequest)));
  }

  public Optional<DeleteUserResponse> delete(Long id) {
    return cacheService.delete(id).map(u -> new DeleteUserResponse(u.id(), u.username(), u.created()));
  }

  /*
   * Responses to PUT method are not cacheable.
   * link:
   */
  public UpdateUserResponse update(UserEntity user) {
    cacheService.update(user);

    return null;
  }

  /*
   * Use PUT APIs primarily to update an existing resource (if the resource does not exist,
   * then API may decide to create a new resource or not).
   */
  public UpdateUserResponse update(Long id, UpdateUserRequest user) {
    // case 1: create if not exist
      Optional<UserEntity> entityById = cacheService.findById(id);

    // case 2: only update user
    var entity = mapToEntity.apply(user);
    return Optional.ofNullable(cacheService.update(entity))
        .map(
            persistEntity -> {
              var userId = persistEntity.id();
              var username = persistEntity.username();
              var created = persistEntity.created();
              return new UpdateUserResponse(userId, username, created);
            })
        .orElseThrow(() -> new IllegalArgumentException("Cannot save user - %s".formatted(user)));
  }

  private final Function<UpdateUserRequest, UserEntity> mapToEntity =
      userRequest ->
          new UserEntity(userRequest.id(), userRequest.username(), userRequest.created());

  private UserEntity mapToEntity(Long id, CreateUserRequest userRequest) {
    return new UserEntity(id, userRequest.username(), LocalDateTime.now());
  }

  private FindUserByIdResponse mapToResponse(UserEntity entity) {
    return new FindUserByIdResponse(entity.id(), entity.username(), entity.created());
  }
}
