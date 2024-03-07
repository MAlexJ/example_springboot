package com.malexj.service;

import com.malexj.model.User;
import com.malexj.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserCacheService {

    private final UserRepository repository;

    @Cacheable(cacheNames = "users")
    public List<User> findAll() {
        return repository.findAll();
    }
}
