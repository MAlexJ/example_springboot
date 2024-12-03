package com.malex.redis_data_store_for_cache.database.service;

import com.malex.redis_data_store_for_cache.cache.service.UserCacheService;
import com.malex.redis_data_store_for_cache.database.entity.UserEntity;
import com.malex.redis_data_store_for_cache.database.mapper.ObjectMapper;
import com.malex.redis_data_store_for_cache.rest.request.CreateUserRequest;
import com.malex.redis_data_store_for_cache.rest.request.UserRequest;
import com.malex.redis_data_store_for_cache.rest.response.UserResponse;
import com.malex.redis_data_store_for_cache.rest.response.UsersResponse;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  private final ObjectMapper mapper;

  private final AtomicLong generatePrimaryKey;

  private final UserCacheService cacheService;

  public UserService(
      ObjectMapper mapper,
      @Qualifier("inMemoryPrimaryKeyGenerator") AtomicLong generatePrimaryKey,
      UserCacheService cacheService) {
    this.mapper = mapper;
    this.generatePrimaryKey = generatePrimaryKey;
    this.cacheService = cacheService;
  }

  public UsersResponse findAll() {
    var userEntities = cacheService.findAll();
    var entities =
        userEntities.stream() //
            .map(mapper::entityToResponse)
            .toList();
    return new UsersResponse(entities, entities.size());
  }

  public Optional<UserResponse> findById(Long id) {
    return cacheService.findById(id).map(mapper::entityToResponse);
  }

  public Optional<UserResponse> save(CreateUserRequest userRequest) {
    return Optional.of(generatePrimaryKey.incrementAndGet())
        .map(newId -> mapper.requestToEntity(userRequest, newId))
        .map(cacheService::save)
        .map(mapper::entityToResponse);
  }

  /*
   * Use PUT APIs primarily to update an existing resource (if the resource does not exist,
   * then API may decide to create a new resource or not).
   */
  public Optional<UserResponse> update(Long id, UserRequest userRequest) {

    // step 1: check if the user exists
    Optional<UserEntity> existUser = cacheService.findById(id);

    return Optional.ofNullable(
        existUser
//            // todo: verify it
//            .or(
//                () -> {
//                  return Optional.of(mapper.requestToEntity(userRequest));
//                })
            .map(
                user -> {
                  // case 1: if user is existed then update user
                  var entity = mapper.requestToEntity(userRequest);
                  var persistEntity = cacheService.update(entity);
                  return mapper.entityToResponse(persistEntity);
                })
            .orElseGet(
                () -> {
                  // case 2: create new user if not exist
                  var entity = mapper.requestToEntity(userRequest);
                  var persistEntity = cacheService.save(entity);
                  return mapper.entityToResponse(persistEntity);
                }));
  }

  public Optional<UserResponse> delete(Long id) {
    return cacheService.delete(id).map(mapper::entityToResponse);
  }
}
