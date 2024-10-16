package com.malex.inroduction.bearer;

import com.malex.inroduction.IntroductionApplication;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(
    name = "bearerOpenFeignWebclient",
    url = "${service.client.url}",
    configuration = BearerOpenFeignInterception.class)
public interface BearerOpenFeignWebclient {

  @GetMapping("/${service.client.path.bearer}")
  IntroductionApplication.BearerResponse getBearerInfo();
}
