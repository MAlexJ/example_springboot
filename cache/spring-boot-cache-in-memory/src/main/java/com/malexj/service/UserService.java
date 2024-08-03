package com.malexj.service;

import com.malexj.model.User;
import com.malexj.repository.UserRepository;
import java.util.Collection;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
// @CacheConfig(cacheNames = "users")
@RequiredArgsConstructor
public class UserService {

  private final UserRepository repository;
  private final CacheManager cacheManager;

  @Cacheable(cacheNames = "users")
  public List<User> findAll() {
    // Collections#UnmodifiableSet
    Collection<String> cacheNames = cacheManager.getCacheNames();
    // Cache >> String name and ConcurrentMap<Object, Object> store
    Cache users = cacheManager.getCache("users");
    log.info("cacheNames - {}", cacheNames);
    log.info("users - {}", users);
    return repository.findAll();
  }

  @Cacheable(cacheNames = "users", key = "#id")
  public User findById(Integer id) {
    // Collections#UnmodifiableSet
    Collection<String> cacheNames = cacheManager.getCacheNames();
    // Cache >> String name and ConcurrentMap<Object, Object> store
    Cache users = cacheManager.getCache("users");
    log.info("cacheNames - {}", cacheNames);
    log.info("users - {}", users);
    return repository.findUserById(id);
  }

  //  @CachePut
  public boolean save(User user) {
    return repository.save(user);
  }

  /** triggers a cache evict operation */
  @CacheEvict(cacheNames = "users", allEntries = true)
  public boolean delete(Integer id) {
    return repository.delete(id);
  }

  @CachePut(cacheNames = "users")
  public boolean update(User user) {
    return repository.update(user);
  }

  public User manualAdding(User user) {
    Collection<String> cacheNames = cacheManager.getCacheNames();
    // Cache >> String name and ConcurrentMap<Object, Object> store
    Cache users = cacheManager.getCache("users");
    log.info("cacheNames - {}", cacheNames);
    log.info("users - {}", users);
    if (users != null) users.put(user.id(), user);
    return user;
  }
}
