### Rate Limiting with Spring Boot multiple instances with Bucket4j, and Redis

In this blog post you learn a way how to write a rate limit protection for your Spring Boot server t
hat runs in multiple instances using bucket4j and Redis.

Rate limiting means an upper threshold exists for how many calls per timeframe against
all server instances are allowed per client.

If the rate by which a client calls the servers is too high, the calls are rejected.

link: https://www.innoq.com/en/blog/2024/03/distributed-rate-limiting-with-spring-boot-and-redis/