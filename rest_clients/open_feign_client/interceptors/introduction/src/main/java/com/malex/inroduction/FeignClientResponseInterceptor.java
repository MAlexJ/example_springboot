package com.malex.inroduction;

import feign.InvocationContext;
import feign.Response;
import feign.ResponseInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class FeignClientResponseInterceptor implements ResponseInterceptor {

  @Override
  public Object intercept(InvocationContext invocationContext, Chain chain) throws Exception {

    try (var response = invocationContext.response()) {
      log.info("Response headers: {}", response.headers());

      var request = response.request();
      log.info("Request url: {}", request.url());

      Response.Body body = response.body();
      log.info("Response body: {}", body);
    }

    return invocationContext.proceed();
  }
}
