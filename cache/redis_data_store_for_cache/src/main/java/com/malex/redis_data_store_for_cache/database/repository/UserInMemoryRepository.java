package com.malex.redis_data_store_for_cache.database.repository;

import com.malex.redis_data_store_for_cache.database.entity.UserEntity;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
public class UserInMemoryRepository {

  private final Map<Long, UserEntity> database;

  public UserInMemoryRepository(@Qualifier("inMemoryUserDatabase") Map<Long, UserEntity> database) {
    this.database = database;
  }

  public List<UserEntity> findAll() {
    return database.values().stream().toList();
  }

  public UserEntity save(UserEntity user) {
    log.trace("Saving user - {}", user);
    return database.merge(user.id(), user, (existUser, newUser) -> newUser);
  }

  public UserEntity update(UserEntity user) {
    log.trace("Updating user - {}", user);
    return database.merge(user.id(), user, (existUser, newUser) -> newUser);
  }

  public Optional<UserEntity> delete(Long id) {
    log.trace("Deleting user - {}", id);
    /*
     * Returns: the previous value associated with key, or null if there was no mapping for key.
     */
    var removeEntity = database.remove(id);
    return Optional.ofNullable(removeEntity);
  }

  public Optional<UserEntity> findUserById(Long id) {
    log.trace("Finding user - {}", id);
    /*
     * Returns: the value to which the specified key is mapped, or null if this map contains no mapping for the key
     */
    return Optional.ofNullable(database.get(id));
  }
}
