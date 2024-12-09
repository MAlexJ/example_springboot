### Spring Boot Caching with Redis

link: https://medium.com/simform-engineering/spring-boot-caching-with-redis-1a36f719309f

Cache: Many applications struggle with the need to store and retrieve data quickly, especially in systems with high
latency.
Due to its speed, Redis is the ideal choice for caching API calls, session states, complex computations,
and database queries.

#### Spring Data Redis

Spring Data Redis, part of the larger Spring Data portfolio, provides easy configuration
and access to Redis from Spring applications.
It offers both low-level and high-level abstractions for interacting with the store,
freeing users from infrastructural concerns.

link: https://spring.io/projects/spring-data-redis

#### Project configuration

Add properties to `.env` file:

```
REDIS_HOST=........redns.redis-cloud.com
REDIS_PORT=11705
REDIS_USERNAME=.....
REDIS_PASSWORD=......
```

#### Provider:

* https://redis.io (documentation)
* https://app.redislabs.com/ (cloud service)

#### Spring Boot Cache with Redis

link: https://www.baeldung.com/spring-boot-redis-cache

#### Configuration

spring documentation: https://docs.spring.io/spring-data/redis/reference/redis/redis-cache.html

#### Time To Live(TTL):

Time To Live(TTL):
A good practice for caching is to ensure that excess and redundant data is not accumulated indefinitely, as this can
result in stale or outdated data being served to users. To serve this, we can take advantage of the time-to-live
property, which is an optional setting that allows us to set the expiration time for cached data. After the specified
time has elapsed, the cached entry is automatically removed from the cache. This makes space for new data to be fetched
and stored in the cache the next time it’s requested. If no value is assigned to the property, it becomes -1 by default,
which means the data will stay in the cache indefinitely.
We have set our time to live property to be 60000 ms in the example, which means that the data will be cleared from the
cache after every minute.

#### Spring Cache

In Spring, the @CachePut annotation allows you to define a cache key using the key attribute,
which supports SpEL (Spring Expression Language).
This enables dynamic generation of cache keys based on method arguments or properties of objects.

Example:
To generate a key based on a field of an object passed as a parameter:

```
record UserEntity(Long id, Strin username){}

@CachePut(cacheNames = "user_cache", key = "#user.id")
public UserEntity update(UserEntity user) {.....}
```

Here:

- #user refers to the method parameter user.
- .id accesses the id field of the user object.

Example with multiple parameters:
You can concatenate multiple fields or parameters to create a composite key:

```
@CachePut(cacheNames = "user_cache", key = "#user.id + '_' + #action")
public User updateUserWithAction(User user, String action) {
    // Update logic
    return user;
}
```

In this case, the cache key is generated using the id field of the user object and the action parameter.

Key Features:

Accessing fields: Ensure the field used in the key is public or has a getter method.

Custom key logic: If needed, you can delegate key generation to a custom method or component:

```
@CachePut(cacheNames = "user_cache", key = "@keyGenerator.generateKey(#user)")
```

Custom key generator:

```
@Component("keyGenerator")
public class KeyGenerator {
    public String generateKey(User user) {
        return user.getId() + ":" + user.getName();
    }
}
```

Notes:

For methods with multiple parameters, you can access specific parameters by name (e.g., #user)
or by position (e.g., #args[0].id).

Using SpEL for key generation ensures flexibility and allows you to compute keys dynamically.
For further details, refer to the Spring Caching documentation and the Spring Expression Language guide.
