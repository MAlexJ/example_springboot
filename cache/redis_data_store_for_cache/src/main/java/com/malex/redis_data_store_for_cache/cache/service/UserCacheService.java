package com.malex.redis_data_store_for_cache.cache.service;

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

import com.malex.redis_data_store_for_cache.database.entity.UserEntity;
import com.malex.redis_data_store_for_cache.database.repository.UserInMemoryRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserCacheService {

    private final UserInMemoryRepository repository;

    private final CacheManager cacheManager;

    @Cacheable(cacheNames = "user_cache")
    public List<UserEntity> findAll() {
        logCacheManagerState("FIND_ALL");
        Collection<String> cacheNames = cacheManager.getCacheNames();
        return repository.findAll();
    }

    @Cacheable(cacheNames = "user_cache", key = "#id")
    public Optional<UserEntity> findById(Long id) {
        // Collections#UnmodifiableSet
        logCacheManagerState("FIND_BY_ID");
        return repository.findUserById(id);
    }

    @CacheEvict(cacheNames = "user_cache", allEntries = true, beforeInvocation = true)
    public UserEntity save(UserEntity user) {
        return repository.save(user);
    }

    /* triggers a cache evict operation */
    @CacheEvict(cacheNames = "user_cache", allEntries = true, beforeInvocation = true)
    public Optional<UserEntity> delete(Long id) {
        return repository.delete(id);
    }

    @CachePut(cacheNames = "user_cache") // not work
    public UserEntity update(UserEntity user) {
        return repository.update(user);
    }

    public UserEntity manualAdding(UserEntity user) {
        Cache users = cacheManager.getCache("user_cache");
        if (users != null) users.put(user.id(), user);
        return user;
    }

    private void logCacheManagerState(String action) {
        // Collections#UnmodifiableSet
        Collection<String> cacheNames = cacheManager.getCacheNames();
        log.info("{} - action,  cache names - {}", action, cacheNames);

        // Cache >> String name and ConcurrentMap<Object, Object> store
        Cache users = cacheManager.getCache("user_cache");
        log.info("{} - action, users in cache - {}", action, users);
    }
}