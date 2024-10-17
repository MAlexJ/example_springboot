package com.malex.logginglevel;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Slf4j
@SpringBootApplication
@RequiredArgsConstructor
public class LoggingLevelApplication implements CommandLineRunner {

  private final MyServiceImpl myService;

  @Override
  public void run(String... args) {
    log.trace("run TRACE Message");
    log.debug("run DEBUG Message");
    log.info("run INFO Message");
    log.warn("run WARN Message");
    log.error("run ERROR Message");
    myService.print();
  }

  public static void main(String[] args) {
    SpringApplication.run(LoggingLevelApplication.class, args);
  }

  @Bean
  public RouterFunction<ServerResponse> personRoute() {
    return route(
        GET("/v1/hello"),
        req ->
            ok().body(Mono.just("Hello World"), String.class)
                .doOnNext(
                    response -> {
                      log.trace("A TRACE Message");
                      log.debug("A DEBUG Message");
                      log.info("An INFO Message");
                      log.warn("A WARN Message");
                      log.error("An ERROR Message");

                      // TRACE level enabled for a class
                      myService.print();
                    })
                .doOnCancel(() -> log.info("Closing connection")));
  }
}
