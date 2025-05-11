package example.java.com.malex.controller;

import example.java.com.malex.service.AsyncService;
import java.util.concurrent.CompletableFuture;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/tasks")
@RequiredArgsConstructor
public class RestApiController {
  private final AsyncService asyncService;

  // Using @Async: void
  @GetMapping("/async_void")
  public ResponseEntity<String> triggerAsyncVoid() {
    asyncService.runAsyncTaskVoid(); // fire-and-forget
    return ResponseEntity.ok("Async void task started.");
  }

  // Using @Async: CompletableFuture
  @GetMapping("/async_return_completable_future")
  public CompletableFuture<ResponseEntity<String>> triggerAsyncWithResult() {
    return asyncService.runAsyncTaskCompletableFuture().thenApply(ResponseEntity::ok);
  }
}
