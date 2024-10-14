package com.malex.enable_logging_level;

import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "openFeignWebclient", url = "https://jsonplaceholder.typicode.com")
public interface OpenFeignWebclient {

  @GetMapping("/todos")
  List<LoggingApplication.Todo> findAll();
}
