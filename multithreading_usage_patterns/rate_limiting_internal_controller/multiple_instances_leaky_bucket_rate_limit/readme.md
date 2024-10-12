### Rate Limiting with Spring Boot multiple instances with Bucket4j, and Redis

In this blog post you learn a way how to write a rate limit protection for your Spring Boot server t
hat runs in multiple instances using bucket4j and Redis.

Rate limiting means an upper threshold exists for how many calls per timeframe against
all server instances are allowed per client.

If the rate by which a client calls the servers is too high, the calls are rejected.

link: https://www.innoq.com/en/blog/2024/03/distributed-rate-limiting-with-spring-boot-and-redis/

We use Lettuce. So we have a Redis server, several instances of the same Spring Boot server with a REST endpoint
that needs rate limit protection.

### Bucket4j

Bucket4j is a Java rate-limiting library that is mainly based on the token-bucket algorithm,
which is by the de-facto standard for rate-limiting in the IT industry.

You can read more about the token bucket by following links:

* Token bucket - Wikipedia page describes the token-bucket algorithm in classical form
  link: https://en.wikipedia.org/wiki/Token_bucket
* Non-formal overview of token-bucket algorithm - the brief overview of the token-bucket algorithm
  link: https://vbukhtoyarov-java.blogspot.com/2021/11/non-formal-overview-of-token-bucket.html

### Redis configuration

#### Lettuce - Advanced Java Redis client

Lettuce is a scalable thread-safe Redis client for synchronous, asynchronous and reactive usage.
Multiple threads may share one connection if they avoid blocking and transactional operations such as BLPOP and
MULTI/EXEC.
Lettuce is built with netty.
Supports advanced Redis features such as Sentinel, Cluster, Pipelining, Auto-Reconnect and Redis data models.

link: https://redis.github.io/lettuce/

#### Execute Redis, locally

Setup Redis locally

```
docker pull redis:latest
docker run -d -p 6379:6379 --name rate-limit redis
```

Locally Redis configuration

```
    private RedisClient redisClient() {
        return RedisClient.create(RedisURI.builder()
            .withHost("localhost")
            .withPort(6379)
            .withSsl(false)
            .build());
    }
```