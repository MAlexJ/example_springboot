package com.malex.error_handling;

import com.malex.error_handling.webservice.OpenFeignClientImpl;
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
public class ErrorHandlingApplication {

  public static void main(String[] args) {
    SpringApplication.run(ErrorHandlingApplication.class, args);
  }

  @RestController
  @RequestMapping("/v1/api")
  @RequiredArgsConstructor
  public static class RestApiController {

    private final OpenFeignClientImpl openFeignClient;

    @GetMapping("/info")
    public ResponseEntity<AuthUserResponse> authUser() {
      return ResponseEntity.ok(openFeignClient.auth());
    }

    @GetMapping("status/{status}")
    public ResponseEntity<Void> status(@PathVariable Integer status) {
      openFeignClient.findStatus(status);
      return ResponseEntity.ok().build();
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
}
