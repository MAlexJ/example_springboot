package com.malexj.service;

import com.malexj.model.UserRecord;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class AppService {

  private final List<UserRecord> users =
      List.of(
          new UserRecord(UUID.randomUUID().toString(), "Alex"),
          new UserRecord(UUID.randomUUID().toString(), "Max"),
          new UserRecord(UUID.randomUUID().toString(), "Stefan"),
          new UserRecord(UUID.randomUUID().toString(), "Cat"),
          new UserRecord(UUID.randomUUID().toString(), "Dog"),
          new UserRecord(UUID.randomUUID().toString(), "Anna"));

  public List<UserRecord> findAllUsers() {
    return users;
  }

  public List<UserRecord> findUsersByName(String name) {
    return users.stream() //
        .filter(u -> u.name().contains(name))
        .toList();
  }
}
