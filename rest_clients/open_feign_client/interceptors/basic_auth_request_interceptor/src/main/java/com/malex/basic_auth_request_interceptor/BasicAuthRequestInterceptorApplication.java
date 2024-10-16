package com.malex.basic_auth_request_interceptor;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@EnableFeignClients
@SpringBootApplication
public class BasicAuthRequestInterceptorApplication {

  public static void main(String[] args) {
    SpringApplication.run(BasicAuthRequestInterceptorApplication.class, args);
  }

  @RestController
  @RequestMapping("/v1/api")
  @RequiredArgsConstructor
  public static class RestApiController {

    private final BasicAuthOpenFeignClient authOpenFeignClient;

    @GetMapping("/info")
    public ResponseEntity<IfoResponse> info() {
      return ResponseEntity.ok(authOpenFeignClient.getInfo());
    }
  }

  public record IfoResponse(Boolean authenticated, String user) {}
}
