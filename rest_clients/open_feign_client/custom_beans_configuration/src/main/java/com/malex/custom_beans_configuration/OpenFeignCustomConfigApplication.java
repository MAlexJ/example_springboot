package com.malex.custom_beans_configuration;

import java.util.List;
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
public class OpenFeignCustomConfigApplication {

  public static void main(String[] args) {
    SpringApplication.run(OpenFeignCustomConfigApplication.class, args);
  }

  /*
   * Rest controller
   */
  @RestController
  @RequestMapping("/v1/api")
  @RequiredArgsConstructor
  public static class RestApiController {

    private final OpenFeignWebclient webclient;

    @GetMapping("/comments")
    public ResponseEntity<List<Comment>> findPosts() {
      return ResponseEntity.ok(webclient.getComments());
    }
  }

  /*
   * Response dto model
   */
  public record Comment(Long postId, Long id, String name, String email, String body) {}
}
