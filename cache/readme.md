### Caching : System Design

video tutorial: https://www.youtube.com/watch?v=iLMlYgQoTIE
info: https://habr.com/ru/articles/734660/

#### Caching : System Design Fundamentals

link: https://medium.com/@abhirup.acharya009/caching-system-design-fundamentals-226795bd9072

Cache is a high-speed data storage layer that sits between the main memory (RAM) and the central processing unit (CPU)
or other components that need frequent access to data.

##### Popular Caches

* Memcached: Memcached is an open-source, high-performance, distributed memory object caching system.
* Redis: Redis is an open-source, in-memory data structure store that can serve as a cache, database, and message
  broker.

##### Cache Writing Policies

Cache Policy is a set of rules which define how the data will be loaded (and evicted) from a cache memory.
A cache is made of copies of data, and is thus transient storage, so when writing we need to decide
when to write to the cache and when to write to the primary data store.

1. Cache Aside Caching Strategy
   In this caching technique, the responsibility for managing the cache is placed on the application rather
   than the underlying data store or the caching layer itself.

2. Read Through Caching Strategy
   In this caching technique, when the application requests data that is not present in the cache (cache miss),
   the cache itself takes responsibility for fetching the data from the underlying data store or database.
   After retrieving the data, it stores it in the cache and then returns it to the application.

3. Write Around Caching Strategy
   In this caching technique, when the application performs a write operation, the data is written directly
   to the underlying data store or database without being immediately stored in the cache.
   This means that the cache is bypassed during write operations, and data is not kept in the cache until it is read
   again.

4. Write Through Caching Strategy
   In this caching technique, when the application performs a write operation, the data is written both to the cache
   and the underlying data store or database simultaneously. This ensures that the data remains consistent
   between the cache and the data store at all times.

5. Write Back Caching Strategy
   In this caching technique, when the application performs a write operation, the data is first written to the
   cache but not immediately to the underlying data store or database. Instead, the cache marks the data
   as “dirty” or “modified” to indicate that it has been modified in the cache but not yet written to the data store.