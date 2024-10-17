package com.malex.error_handling.webservice;

import com.malex.error_handling.ErrorHandlingApplication;
import com.malex.error_handling.config.FeignClientConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
    name = "openFeignClientImpl",
    url = "${service.client.url}",
    configuration = FeignClientConfiguration.class)
public interface OpenFeignClientImpl {

  @GetMapping("/${service.client.path.auth}/${service.client.user}/${service.client.password}")
  ErrorHandlingApplication.AuthUserResponse auth();

  @GetMapping("/${service.client.path.status}")
  void findStatus(@PathVariable Integer status);
}
