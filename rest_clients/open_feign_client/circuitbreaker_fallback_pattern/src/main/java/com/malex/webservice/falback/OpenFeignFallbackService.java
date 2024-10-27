package com.malex.webservice.falback;

import com.malex.webservice.OpenFeignClientImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;

@Slf4j
@Component
public class OpenFeignFallbackService implements OpenFeignClientImpl {

  @Override
  public String findStatus(@PathVariable Integer status) {
    log.info(">>> FallbackService findStatus");
    return "Fallback";
  }
}
