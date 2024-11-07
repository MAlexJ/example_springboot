package com.malex;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

/**
 * {@link DispatcherServlet#initHandlerMappings(ApplicationContext)} - init DispatcherServlet
 *
 * <p>{@link DispatcherServlet#getHandler(HttpServletRequest)} - request handler
 *
 * <p>{@link HandlerMapping} - HandlerMapping interface
 *
 * <p>{@link RequestMappingHandlerMapping} - base HandlerMapping impl for rest api
 */
@SpringBootApplication
public class HandlerMappingInterfacesApp {

  public static void main(String[] args) {
    SpringApplication.run(HandlerMappingInterfacesApp.class, args);
  }

  @RestController
  @RequestMapping("/api/cats")
  public static class RestApiController {

    @GetMapping("{id}")
    public ResponseEntity<Cat> findCatById(@PathVariable Integer id) {
      return ResponseEntity.ok(new Cat(id, "Stefan"));
    }
  }

  public record Cat(int id, String name) {}
}
