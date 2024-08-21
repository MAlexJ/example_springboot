#### Spring Boot Caching with Redis

link: https://medium.com/simform-engineering/spring-boot-caching-with-redis-1a36f719309f

Cache: Many applications struggle with the need to store and retrieve data quickly, especially in systems with high
latency.
Due to its speed, Redis is the ideal choice for caching API calls, session states, complex computations,
and database queries.

### Project configuration

###### Add properties to `.env` file:

```
DATA_REDIS_HOST=........redns.redis-cloud.com
DATA_REDIS_PORT=11705
DATA_REDIS_USERNAME=.....
DATA_REDIS_PASSWORD=......
```

###### Provider:

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