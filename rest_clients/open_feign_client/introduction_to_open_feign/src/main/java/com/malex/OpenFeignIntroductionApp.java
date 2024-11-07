package com.malex;

import java.util.List;
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
public class OpenFeignIntroductionApp {

  public static void main(String[] args) {
    SpringApplication.run(OpenFeignIntroductionApp.class, args);
  }

  /*
   * Rest controller
   */
  @RestController
  @RequestMapping("/v1/api/posts")
  @RequiredArgsConstructor
  public static class RestApiController {

    private final OpenFeignWebclient webclient;

    @GetMapping()
    public ResponseEntity<List<Post>> findPosts() {
      return ResponseEntity.ok(webclient.getPosts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Post> findPostById(@PathVariable Long id) {
      return ResponseEntity.ok(webclient.getPostById(id));
    }
  }

  /*
   * Response dto model
   */
  public record Post(Long userId, Long id, String title, String body) {}
}
