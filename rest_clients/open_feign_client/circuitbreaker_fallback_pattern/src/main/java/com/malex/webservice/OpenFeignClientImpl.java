package com.malex.webservice;

import com.malex.webservice.falback.OpenFeignFallbackService;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
    name = "openFeignClientImpl",
    url = "${service.client.url}",
    fallback = OpenFeignFallbackService.class)
public interface OpenFeignClientImpl {

  @GetMapping("/${service.client.path.status}")
  String findStatus(@PathVariable Integer status);
}
