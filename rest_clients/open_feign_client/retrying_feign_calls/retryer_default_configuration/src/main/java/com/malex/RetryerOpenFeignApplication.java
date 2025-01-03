package com.malex;

import com.malex.webservice.OpenFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@EnableFeignClients
@SpringBootApplication
public class RetryerOpenFeignApplication {

  public static void main(String[] args) {
    SpringApplication.run(RetryerOpenFeignApplication.class, args);
  }

  @RestController
  @RequiredArgsConstructor
  public static class RestApiController {

    private final OpenFeignClient openFeignClient;

    @GetMapping("/v1/info/{status}")
    public ResponseEntity<String> info(@PathVariable Integer status) {
      return ResponseEntity.ok(openFeignClient.info(status));
    }
  }
}
