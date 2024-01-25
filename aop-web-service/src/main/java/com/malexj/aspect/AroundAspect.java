package com.malexj.aspect;

import com.malexj.exception.WebApplicationException;
import java.util.Arrays;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class AroundAspect {

  @Around("com.malexj.aspect.ServiceMethodsPointcut.allPublicServiceMethods()")
  public Object aroundAdvice(ProceedingJoinPoint joinPoint) {
    Signature signature = joinPoint.getSignature();
    String methodName = joinPoint.getSignature().getName();
    log.info(
        "class: {}, method: {}, args: {}",
        signature.getDeclaringTypeName(),
        methodName,
        Optional.of(joinPoint.getArgs()).map(Arrays::toString).orElse(""));
    Object proceed;
    try {
      proceed = joinPoint.proceed();
    } catch (Throwable e) {
      log.warn(e.getMessage());
      throw new WebApplicationException("Aspects exception:", e);
    }
    return proceed;
  }
}
