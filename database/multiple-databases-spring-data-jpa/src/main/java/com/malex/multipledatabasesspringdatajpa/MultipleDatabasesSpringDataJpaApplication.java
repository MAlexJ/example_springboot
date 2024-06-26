package com.malex.multipledatabasesspringdatajpa;

import com.malex.multipledatabasesspringdatajpa.entity.mysql.ProductEntity;
import com.malex.multipledatabasesspringdatajpa.repository.mysql.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
@RequiredArgsConstructor
public class MultipleDatabasesSpringDataJpaApplication implements CommandLineRunner {

  private final ProductRepository productRepository;

  public static void main(String[] args) {
    SpringApplication.run(MultipleDatabasesSpringDataJpaApplication.class, args);
  }

  @Override
  public void run(String... args) {
    log.info("Initializing application");

    /* Spring default datasource properties:
            <pre>
        package: org.springframework.boot.autoconfigure.jdbc

        @ConfigurationProperties(prefix = "spring.datasource")
        public class DataSourceProperties implements BeanClassLoaderAware, InitializingBean {.......}
    </pre>
             */

    // 1. MySQL
    var product = new ProductEntity();
    product.setName("Apple M1");
    product.setDescription("Good notebook");
    var productEntity = productRepository.save(product);
    log.info("Product saved: {}", productEntity);

    // 2. Postgresql
    log.info("Finalizing application");
  }
}
