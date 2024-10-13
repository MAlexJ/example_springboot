package com.malexj.aspect;

import org.aspectj.lang.annotation.Pointcut;

public class ServiceMethodsPointcut {

  @Pointcut(
      "execution(* com.malexj.service.BookService.find*(..)) "
          + "|| execution(* com.malexj.service.BookService.add*(..))")
  public void allPublicServiceMethods() {}
}
