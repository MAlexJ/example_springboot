package com.malex.http_exchange;

import com.malex.HttpExchangesApplication;
import java.util.List;
import org.springframework.web.service.annotation.GetExchange;

public interface PostExchangeInterface {

  @GetExchange("/posts")
  List<HttpExchangesApplication.Post> findAll();
}
