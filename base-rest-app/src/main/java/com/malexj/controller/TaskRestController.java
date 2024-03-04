package com.malexj.controller;

import com.malexj.model.Task;
import com.malexj.model.request.NewTaskPayload;
import com.malexj.repository.TasksRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskRestController {

  private final TasksRepository repository;

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<Task>> handleGetTasks() {
    return ResponseEntity.ok(repository.findAll());
  }

  @PostMapping
  public ResponseEntity<Object> create(@RequestBody NewTaskPayload payload) {
    return ResponseEntity.ok("");
  }
}
