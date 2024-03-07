package com.malexj;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * Configuration:<br>
 * 1. Add dependency to the project - spring-boot-starter-cache <br>
 * 2. Enable cache using annotation @EnableCaching in configuration class
 */
@EnableCaching
@SpringBootApplication
public class CacheApplication {

  public static void main(String[] args) {
    SpringApplication.run(CacheApplication.class, args);
  }
}
