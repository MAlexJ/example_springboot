package com.malexj.repository;

import com.malexj.model.User;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;
import lombok.SneakyThrows;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {

  private final List<User> users = new CopyOnWriteArrayList<>();

  @SneakyThrows
  public List<User> findAll() {
    TimeUnit.SECONDS.sleep(2);
    return users;
  }

  @SneakyThrows
  public boolean save(User user) {
    TimeUnit.SECONDS.sleep(2);
    return users.add(user);
  }

  @SneakyThrows
  public boolean update(User user) {
    return findUserByIdOpt(user.id()) //
        .map(users::remove)
        .map(u -> users.add(user))
        .isPresent();
  }

  public boolean delete(Integer id) {
    return findUserByIdOpt(id) //
        .map(users::remove)
        .isPresent();
  }

  @SneakyThrows
  public User findUserById(int id) {
    TimeUnit.SECONDS.sleep(2);
    return findUserByIdOpt(id) //
        .orElseThrow(() -> new RuntimeException(String.format("User by id - '%s' not found!", id)));
  }

  @SneakyThrows
  public Optional<User> findUserByIdOpt(Integer id) {
    TimeUnit.SECONDS.sleep(2);
    return users.stream() //
        .filter(u -> u.id().equals(id))
        .findFirst();
  }
}
