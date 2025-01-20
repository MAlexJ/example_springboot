package com.malex.restful.repository;

import com.malex.restful.model.User;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {

  private final AtomicLong sequence = new AtomicLong();

  private final List<User> repository;

  public UserRepository() {
    repository = new CopyOnWriteArrayList<>();
  }

  public List<User> findAll() {
    return repository;
  }

  public List<User> findAll(String name) {
    return repository.stream().filter(user -> user.name().equals(name)).toList();
  }

  public User save(User user) {
    long newUserId = sequence.getAndIncrement();
    var persistUser = new User(newUserId, user);
    repository.add(persistUser);
    return persistUser;
  }

  public Optional<User> findById(Long id) {
    return repository.stream() //
        .filter(user -> user.id().equals(id))
        .findFirst();
  }

  public Optional<User> deleteById(Long id) {
    return findById(id).map(user -> repository.remove(user) ? user : null);
  }
}
