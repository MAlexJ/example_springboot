package com.malex.base_controller_annotation.request_param;

import java.util.Optional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class RequestParamController {

  /*
   * By default, method parameters that use this annotation are required
   */
  @GetMapping("/request-param")
  public ResponseEntity<String> requestParam(
      @RequestParam String param) // by default param is required
      {
    return ResponseEntity.ok(param);
  }

  /*
   * we can specify that a method parameter is optional by setting the @RequestParam annotation’s required flag to false
   */
  @GetMapping("/request-param-not-required")
  public ResponseEntity<String> requestParamNotRequired(
      @RequestParam(required = false) String param) {
    return ResponseEntity.ok(String.valueOf(param));
  }

  /*
   * or by declaring the argument with a java.util.Optional wrapper.
   */
  @GetMapping("/request-param-not-required-optional")
  public ResponseEntity<String> requestParamNotRequiredOPt(@RequestParam Optional<String> param) {
    return ResponseEntity.ok(param.orElse("null"));
  }
}
