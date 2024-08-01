package com.malex.routing;

import static org.springframework.web.reactive.function.server.RequestPredicates.DELETE;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.PUT;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

import com.malex.handler.RestHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

@Component
public class RestRouterFunction {

  @Bean
  public RouterFunction<ServerResponse> routes(RestHandler handler) {
    return route(GET("/users"), handler::all)
        .andRoute(POST("/users"), handler::create)
        .andRoute(GET("/users/{id}"), handler::get)
        .andRoute(PUT("/users/{id}"), handler::update)
        .andRoute(DELETE("/users/{id}"), handler::delete);
  }
}
