package com.malex;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class MimeTypeVersioningApp {

  public static void main(String[] args) {
    SpringApplication.run(MimeTypeVersioningApp.class, args);
  }

  /*
   * Media Types
   *
   * If we would like to define the structure of the resource that we are rendering or consuming,
   * we can use media type to define.
   * It also helps in versioning the API.
   *
   * consumes - matched only if the Content-Type request header matches
   *
   * produces - matched only if the Accept request header matches
   */
  @RestController
  @RequestMapping("/students")
  public static class RestApiController {

    @GetMapping(produces = "application/vnd.amitph.students.v1+json")
    public ResponseEntity<RestApiController.StudentV1> getStudentV1() {
      return ResponseEntity.ok(new StudentV1(1, "Stefan", "email"));
    }

    @GetMapping(produces = "application/vnd.amitph.students.v2+json")
    public ResponseEntity<RestApiController.StudentV2> getStudentV2() {
      return ResponseEntity.ok(new StudentV2(2, "Stefan"));
    }

    public record StudentV1(Integer id, String name, String email) {}

    public record StudentV2(Integer id, String name) {}
  }
}
