package com.malex.basic_auth_request_interceptor;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "authOpenFeignWebclient", url = "${service.client.url}")
public interface BasicAuthOpenFeignClient {

  @GetMapping("/${service.client.path.auth}/${service.client.user}/${service.client.password}")
  BasicAuthRequestInterceptorApplication.IfoResponse getInfo();
}
