package com.malex;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestApiController {

  @PostMapping("/dto")
  public ResponseEntity<RestDto> dto(@RequestBody RestDto dto) {

    if (dto.id == null) {
      throw new RuntimeException("id is null");
    }

    if (dto.name == null) {
      throw new IllegalArgumentException("name is null");
    }

    return ResponseEntity.ok(dto);
  }

  public record RestDto(Integer id, String name) {}
}
