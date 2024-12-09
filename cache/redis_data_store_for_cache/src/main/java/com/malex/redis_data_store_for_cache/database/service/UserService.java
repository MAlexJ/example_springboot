package com.malex.redis_data_store_for_cache.database.service;

import com.malex.redis_data_store_for_cache.cache.service.UserCacheService;
import com.malex.redis_data_store_for_cache.database.entity.UserEntity;
import com.malex.redis_data_store_for_cache.database.mapper.ObjectMapper;
import com.malex.redis_data_store_for_cache.rest.request.UserRequest;
import com.malex.redis_data_store_for_cache.rest.response.UserResponse;
import com.malex.redis_data_store_for_cache.rest.response.UsersResponse;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Slf4j
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
    log.info("Found users - {}", userEntities.size());
    var entities =
        userEntities.stream() //
            .map(mapper::entityToResponse)
            .toList();
    return new UsersResponse(entities, entities.size());
  }

  public Optional<UserResponse> findById(Long id) {
    Optional<UserEntity> userEntityOpt = cacheService.findById(id);
    log.trace("Find user by id - {}, user - {}", id, userEntityOpt);
    return userEntityOpt.map(mapper::entityToResponse);
  }

  public Optional<UserResponse> save(UserRequest userRequest) {
    long newId = generatePrimaryKey.incrementAndGet();
    log.trace("Save user - {}, with generated id - {}", userRequest, newId);
    return Optional.of(mapper.requestToEntity(newId, userRequest))
        .map(cacheService::save)
        .map(mapper::entityToResponse);
  }

  /*
   * Use PUT APIs primarily to update an existing resource (if the resource does not exist,
   * then API may decide to create a new resource or not).
   */
  public Optional<UserResponse> update(Long id, UserRequest userRequest) {
    // step 1: check if the user exists
    var userEntityByIdOpt = cacheService.findById(id);

    userEntityByIdOpt.ifPresent(
        entity -> log.trace("Update user - {}, with new user params - {}", entity, userRequest));

    return userEntityByIdOpt
        // case 2: if user is existed then update this user
        .map(existEntity -> mapper.requestToEntity(userRequest))
        .map(cacheService::update)
        .map(mapper::entityToResponse)
        // case 3: save new record if user not found
        .or(() -> save(userRequest));
  }

  public Optional<UserResponse> delete(Long id) {
    log.trace("Delete user by id - {}", id);
    return cacheService.delete(id).map(mapper::entityToResponse);
  }
}
