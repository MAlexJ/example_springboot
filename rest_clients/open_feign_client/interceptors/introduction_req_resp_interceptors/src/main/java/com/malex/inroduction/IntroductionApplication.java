package com.malex.inroduction;

import com.malex.inroduction.auth.AuthOpenFeignWebclient;
import com.malex.inroduction.bearer.BearerOpenFeignWebclient;
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
public class IntroductionApplication {

  public static void main(String[] args) {
    SpringApplication.run(IntroductionApplication.class, args);
  }

  @RestController
  @RequestMapping("/v1/api")
  @RequiredArgsConstructor
  public static class RestApiController {

    private final AuthOpenFeignWebclient feignWebclient;
    private final BearerOpenFeignWebclient bearerWebclient;

    @GetMapping("/info")
    public ResponseEntity<AuthUserResponse> getUserInfo() {
      return ResponseEntity.ok(feignWebclient.getInfo());
    }

    @GetMapping("/bearer")
    public ResponseEntity<BearerResponse> getBearerInfo() {
      return ResponseEntity.ok(bearerWebclient.getBearerInfo());
    }
  }

  /*
   * Auth response:
   *
   * {
   *  "authenticated": true,
   *  "user": "user"
   * }
   */
  public record AuthUserResponse(Boolean authenticated, String user) {}

  /*
   * Bearer response:
   *
   * {
   *   "authenticated": true,
   *   "token": "x_x_x_x"
   *  }
   */
  public record BearerResponse(Boolean authenticated, String token) {}
}
