package com.malex.aop_custom_annotation.aop.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/*
 * Detecting the rate limit
 * We start by writing an annotation @WithRateLimitProtection that allows to mark HTTP endpoints
 * like the above processRequest one that should have a rate limit protection.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface WithRateLimitProtection {}
