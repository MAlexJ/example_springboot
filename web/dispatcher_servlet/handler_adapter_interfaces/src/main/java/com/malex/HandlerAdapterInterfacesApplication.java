package com.malex;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.HandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

/**
 * {@link DispatcherServlet#initHandlerAdapters(ApplicationContext)} - init DispatcherServlet
 *
 * <p>{@link DispatcherServlet#getHandler(HttpServletRequest)} - request handler
 *
 * <p>{@link HandlerAdapter} - HandlerAdapter interface
 *
 * <p>{@link RequestMappingHandlerAdapter} - base HandlerAdapter for rest api
 */
@SpringBootApplication
public class HandlerAdapterInterfacesApplication {

  public static void main(String[] args) {
    SpringApplication.run(HandlerAdapterInterfacesApplication.class, args);
  }
}
