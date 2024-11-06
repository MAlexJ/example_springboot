package com.malex;

import java.util.List;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * {@link DispatcherServlet#initHandlerAdapters(ApplicationContext)} (ApplicationContext)} - init
 * DispatcherServlet
 *
 * <p>{@link HttpMessageConverter} - HttpMessageConverter interface
 *
 * <p>{@link MappingJackson2HttpMessageConverter} - json MessageConverter class
 *
 * <p>{@link WebMvcConfigurationSupport} - class
 *
 * <p>{@link WebMvcConfigurationSupport#getMessageConverters()} - initialization default
 * MessageConverters
 *
 * <p>{@link WebMvcConfigurationSupport#jackson2Present} - variable that is responsible for jackson
 * json type
 *
 * <p>{@link WebMvcConfigurationSupport#addDefaultHttpMessageConverters(List)} - add default (JSON)
 */
@SpringBootApplication
public class HttpMessageConverterInterfacesApp {

  public static void main(String[] args) {
    SpringApplication.run(HttpMessageConverterInterfacesApp.class, args);
  }

  @RestController
  @RequestMapping("/api/dogs")
  public static class RestApiController {

    @GetMapping("/{id}")
    public ResponseEntity<Dog> findGogById(@PathVariable Integer id) {
      return ResponseEntity.ok(new Dog(id, "Hot dog"));
    }
  }

  public record Dog(Integer id, String name) {}
}
