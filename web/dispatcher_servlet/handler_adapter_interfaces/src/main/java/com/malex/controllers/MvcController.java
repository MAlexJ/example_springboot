package com.malex.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.HandlerAdapter;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MvcController {

  /**
   * {@link DispatcherServlet#initHandlerAdapters(ApplicationContext)} - init DispatcherServlet
   *
   * <p>{@link DispatcherServlet#getHandler(HttpServletRequest)} - request handler
   *
   * <p>{@link HandlerAdapter} - HandlerAdapter interface
   */
  @GetMapping("/greeting")
  public ModelAndView sayHello() {
    ModelAndView model = new ModelAndView("greeting");
    model.addObject("message", "Hello!");
    return model;
  }
}
