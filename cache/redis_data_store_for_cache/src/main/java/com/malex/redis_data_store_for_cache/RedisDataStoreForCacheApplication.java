package com.malex.redis_data_store_for_cache;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class RedisDataStoreForCacheApplication {

	public static void main(String[] args) {
		SpringApplication.run(RedisDataStoreForCacheApplication.class, args);
	}

}
