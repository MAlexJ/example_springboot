package com.malex.custom_beans_configuration;

import static com.malex.custom_beans_configuration.OpenFeignCustomConfigApplication.*;

import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(
    value = "openFeignWebclient",
    url = "https://jsonplaceholder.typicode.com/",
    configuration = FeignClientConfiguration.class)
public interface OpenFeignWebclient {

  @GetMapping(value = "/comments", produces = "application/json")
  List<Comment> getComments();
}
