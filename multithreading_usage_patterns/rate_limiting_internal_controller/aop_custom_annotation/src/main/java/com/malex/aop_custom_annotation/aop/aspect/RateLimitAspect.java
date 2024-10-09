package com.malex.aop_custom_annotation.aop.aspect;

import com.malex.aop_custom_annotation.aop.annotation.WithRateLimitProtection;
import com.malex.aop_custom_annotation.exception.RateLimitException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
public class RateLimitAspect {

  public static final String ERROR_MESSAGE =
      "To many request at endpoint %s from IP %s! Please try again after %d milliseconds!";

  private final ConcurrentHashMap<String, List<Long>> requestCounts = new ConcurrentHashMap<>();

  @Value("${APP_RATE_LIMIT:#{1}}")
  private int rateLimit;

  @Value("${APP_RATE_DURATIONINMS:#{60000}}")
  private long rateDuration;

  /**
   * Executed by each call of a method annotated with {@link WithRateLimitProtection} which should
   * be an HTTP endpoint.
   *
   * <p>Counts calls per remote address.<br>
   *
   * <p>Calls older than {@link #rateDuration} milliseconds will be forgotten. <br>
   * If there have been more than {@link #rateLimit} calls within {@link #rateDuration} milliseconds
   * from a remote address, a {@link RateLimitException} will be thrown.
   *
   * @throws RateLimitException iff rate limit for a given remote address has been exceeded
   */
  @Before("@annotation(com.malex.aop_custom_annotation.aop.annotation.WithRateLimitProtection)")
  public void rateLimit() {

    /*
     * Holder class to expose the web request in the form of a thread-bound RequestAttributes object.
     */
    final ServletRequestAttributes requestAttributes =
        (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();

    final String key = requestAttributes.getRequest().getRemoteAddr();

    final long currentTime = System.currentTimeMillis();
    requestCounts.putIfAbsent(key, new ArrayList<>());
    requestCounts.get(key).add(currentTime);
    cleanUpRequestCounts(currentTime);

    if (requestCounts.get(key).size() > rateLimit) {
      throw new RateLimitException(
          String.format(
              ERROR_MESSAGE, requestAttributes.getRequest().getRequestURI(), key, rateDuration));
    }
  }

  private void cleanUpRequestCounts(final long currentTime) {
    requestCounts
        .values()
        .forEach(
            l -> {
              l.removeIf(t -> timeIsTooOld(currentTime, t));
            });
  }

  private boolean timeIsTooOld(final long currentTime, final long timeToCheck) {
    return currentTime - timeToCheck > rateDuration;
  }
}
