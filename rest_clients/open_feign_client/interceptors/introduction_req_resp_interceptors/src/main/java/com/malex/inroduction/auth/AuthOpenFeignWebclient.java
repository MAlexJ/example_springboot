package com.malex.inroduction.auth;

import com.malex.inroduction.IntroductionApplication;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "authOpenFeignWebclient", url = "${service.client.url}")
public interface AuthOpenFeignWebclient {

  @GetMapping("/${service.client.path.auth}/${service.client.user}/${service.client.password}")
  IntroductionApplication.AuthUserResponse getInfo();
}
