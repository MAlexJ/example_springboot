package com.malex.leaky_bucket_with_bucket4j_and_redis.filter;

import io.github.bucket4j.Bucket;
import io.github.bucket4j.BucketConfiguration;
import io.github.bucket4j.ConsumptionProbe;
import io.github.bucket4j.distributed.proxy.ProxyManager;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Order(1)
@RequiredArgsConstructor
public class RateLimitFilter implements Filter {

  private final Supplier<BucketConfiguration> bucketConfiguration;

  private final ProxyManager<String> proxyManager;

  @Override
  public void doFilter(
      ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
      throws IOException, ServletException {

    var httpRequest = (HttpServletRequest) servletRequest;

    var key = httpRequest.getRemoteAddr();
    log.info("Http remote address from request - {}", key);

    Bucket bucket = proxyManager.builder().build(key, bucketConfiguration);

    ConsumptionProbe probe = bucket.tryConsumeAndReturnRemaining(1);
    log.debug(">>>>>>>>remainingTokens: {}", probe.getRemainingTokens());

    if (probe.isConsumed()) {

      filterChain.doFilter(servletRequest, servletResponse);

    } else {
      HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
      httpResponse.setContentType("text/plain");
      httpResponse.setHeader(
          "X-Rate-Limit-Retry-After-Seconds",
          "" + TimeUnit.NANOSECONDS.toSeconds(probe.getNanosToWaitForRefill()));
      httpResponse.setStatus(429);
      httpResponse.getWriter().append("Too many requests");
    }
  }
}
