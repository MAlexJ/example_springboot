package com.malex.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.HandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

@RestController
@RequestMapping("/v1")
public class RestApiController {

  /**
   * {@link DispatcherServlet#initHandlerAdapters(ApplicationContext)} - init DispatcherServlet
   *
   * <p>{@link DispatcherServlet#getHandler(HttpServletRequest)} - request handler
   *
   * <p>{@link HandlerAdapter} - HandlerAdapter interface *
   *
   * <p>{@link RequestMappingHandlerAdapter} - base HandlerAdapter for rest api
   */
  @GetMapping("/hello")
  public ResponseEntity<String> hello() {
    return ResponseEntity.ok("Hello World");
  }
}
