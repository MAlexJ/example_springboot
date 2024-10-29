package com.malex.shutdown_spring_boot_application;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/api")
public class RestApiController {

  private final Logger log = LoggerFactory.getLogger(RestApiController.class);

  private final ConfigurableApplicationContext applicationContext;

  public RestApiController(ConfigurableApplicationContext applicationContext) {
    this.applicationContext = applicationContext;
  }

  @GetMapping("/close/application/context")
  public ResponseEntity<Void> closeApplicationContext() {
    log.warn("Spring Boot application started");
    new Thread(
            () -> {
              /*
               * We can also call the close() method directly using the application context.
               * This destroys all the beans, releases the locks, and then closes the bean factory.
               */
              applicationContext.close();
            })
        .start();
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @GetMapping("/spring/application/exit")
  public ResponseEntity<Void> d() {
    log.warn("SpringApplication.exit()");
    new Thread(
            () -> {
              int exitCode = SpringApplication.exit(applicationContext, new ExitCodeGenerator() {
                @Override
                public int getExitCode() {
                  // return the error code
                  return 0;
                }
              });

              System.exit(exitCode);
            })
        .start();
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }



}
