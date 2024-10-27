package com.malex;

import com.malex.webservice.OpenFeignClientImpl;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@EnableFeignClients
@SpringBootApplication
public class CircuitBreakerFallbackPatternApplication {

  public static void main(String[] args) {
    SpringApplication.run(CircuitBreakerFallbackPatternApplication.class, args);
  }

  @RestController
  @RequestMapping("/v1/api")
  @RequiredArgsConstructor
  public static class RestApiController {

    private final OpenFeignClientImpl openFeignClient;

    @GetMapping("status/{status}")
    public ResponseEntity<String> status(@PathVariable Integer status) {
      String body = Optional.ofNullable(openFeignClient.findStatus(status)).orElse("Success");
      return ResponseEntity.ok(body);
    }
  }
}
