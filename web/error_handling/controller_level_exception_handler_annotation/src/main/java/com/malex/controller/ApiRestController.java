package com.malex.controller;

import com.malex.exception.CustomException;
import com.malex.service.Webservice;
import jakarta.servlet.http.HttpServletRequest;
import java.util.logging.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/api")
public class ApiRestController {

  private static final Logger LOG = Logger.getLogger(ApiRestController.class.getName());

  private final Webservice webservice;

  public ApiRestController(Webservice webservice) {
    this.webservice = webservice;
  }

  @GetMapping("/info")
  public ResponseEntity<String> info() {
    return ResponseEntity.ok(webservice.getInfo());
  }

  /*
   * We can use @ExceptionHandler to annotate methods that Spring automatically invokes
   * when the given exception occurs.
   *
   * We can specify the exception either with the annotation or by declaring it as a method parameter,
   * which allows us to read out details from the exception object to handle it correctly
   */
  @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
  @ExceptionHandler(CustomException.class)
  public void handleConflict(CustomException ex, HttpServletRequest request) {
    LOG.warning("request %s, error: %s ".formatted(request.getRequestURI(), ex.getMessage()));
  }
}
