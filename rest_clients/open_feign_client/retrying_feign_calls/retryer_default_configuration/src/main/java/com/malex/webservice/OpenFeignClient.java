package com.malex.webservice;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "openFeignClient", url = "${service.client.url}")
public interface OpenFeignClient {

  @GetMapping("/${service.client.path.status}")
  String info(@PathVariable Integer status);
}
