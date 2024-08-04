package com.malex.redis_data_store_for_cache.service;

import com.malex.redis_data_store_for_cache.model.User;
import com.malex.redis_data_store_for_cache.repository.UserRepository;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
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
@RequiredArgsConstructor
public class UserCacheService {

  private final UserRepository repository;
  private final CacheManager cacheManager;

  @Cacheable(cacheNames = "user_cache")
  public List<User> findAll() {
    // Collections#UnmodifiableSet
    Collection<String> cacheNames = cacheManager.getCacheNames();
    // Cache >> String name and ConcurrentMap<Object, Object> store
    Cache users = cacheManager.getCache("user_cache");
    log.info("cacheNames - {}", cacheNames);
    log.info("users - {}", users);
    return repository.findAll();
  }

  @Cacheable(cacheNames = "user_cache", key = "#id")
  public Optional<User> findById(Integer id) {
    // Collections#UnmodifiableSet
    Collection<String> cacheNames = cacheManager.getCacheNames();
    // Cache >> String name and ConcurrentMap<Object, Object> store
    Cache users = cacheManager.getCache("user_cache");
    log.info("cacheNames - {}", cacheNames);
    log.info("users - {}", users);
    return repository.findUserById(id);
  }

  @CacheEvict(cacheNames = "user_cache", allEntries = true, beforeInvocation = true)
  public boolean save(User user) {
    return repository.save(user);
  }

  /** triggers a cache evict operation */
  @CacheEvict(cacheNames = "user_cache", allEntries = true, beforeInvocation = true)
  public boolean delete(Integer id) {
    return repository.delete(id);
  }

  @CachePut(cacheNames = "user_cache")
  public boolean update(User user) {
    return repository.update(user);
  }

  public User manualAdding(User user) {
    Collection<String> cacheNames = cacheManager.getCacheNames();
    // Cache >> String name and ConcurrentMap<Object, Object> store
    Cache users = cacheManager.getCache("user_cache");
    log.info("cacheNames - {}", cacheNames);
    log.info("users - {}", users);
    if (users != null) users.put(user.id(), user);
    return user;
  }
}
