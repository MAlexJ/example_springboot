package com.malex.redis_data_store_for_cache.database.repository;

import com.malex.redis_data_store_for_cache.database.entity.UserEntity;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
public class UserInMemoryRepository {

  private final Map<Long, UserEntity> database;

  public UserInMemoryRepository(@Qualifier("inMemoryUserDatabase") Map<Long, UserEntity> database) {
    this.database = database;
  }

  public List<UserEntity> findAll() {
    sleepTwoSecond();
    return database.values().stream().toList();
  }

  public UserEntity save(UserEntity user) {
    return database.merge(user.id(), user, (existUser, newUser) -> newUser);
  }

  public UserEntity update(UserEntity user) {
    return database.merge(user.id(), user, (existUser, newUser) -> newUser);
  }

  public Optional<UserEntity> delete(Long id) {
    /*
     * Returns: the previous value associated with key, or null if there was no mapping for key.
     */
    var removeEntity = database.remove(id);
    return Optional.ofNullable(removeEntity);
  }

  public Optional<UserEntity> findUserById(Long id) {
    sleepTwoSecond();

    /*
     * Returns: the value to which the specified key is mapped, or null if this map contains no mapping for the key
     */
    return Optional.ofNullable(database.get(id));
  }

  @SneakyThrows
  private void sleepTwoSecond() {
    TimeUnit.SECONDS.sleep(2);
  }
}
