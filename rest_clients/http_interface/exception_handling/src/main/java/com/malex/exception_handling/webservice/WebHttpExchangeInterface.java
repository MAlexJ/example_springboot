package com.malex.exception_handling.webservice;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;

public interface WebHttpExchangeInterface {

  @GetExchange("status/{status}")
  void status(@PathVariable Integer status);
}
