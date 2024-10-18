package com.malex.handler_exception_resolver_implementation.exception.resolver;

import com.malex.handler_exception_resolver_implementation.exception.ApplicationException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

@Component
public class RestApiExceptionResolver implements HandlerExceptionResolver {

  @Override
  public ModelAndView resolveException(
      HttpServletRequest request,
      HttpServletResponse response,
      Object object,
      Exception exception) {

    try {
      response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, exception.getMessage());
    } catch (IOException e) {
      throw new ApplicationException(e);
    }

    ModelAndView model = new ModelAndView();
    model.setView(new MappingJackson2JsonView());
    model.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
    model.addObject("exception", exception.getMessage());
    return model;
  }
}
