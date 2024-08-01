package com.malex.handler;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import com.malex.dto.UserResponse;
import com.malex.repository.CrudRepository;
import com.malex.routing.RestRouterFunction;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

/**
 * Link to test: https://blog.knoldus.com/spring-webflux-testing-your-router-functions-with-webtestclient/
 */
@ContextConfiguration(classes = {RestRouterFunction.class, RestHandler.class})
@WebFluxTest
class RestHandlerTest {

  @Autowired private ApplicationContext context;

  @MockBean(name = "userService")
  private CrudRepository repository;

  private WebTestClient testClient;

  @BeforeEach
  public void setUp() {
    testClient = WebTestClient.bindToApplicationContext(context).build();
  }

  @Test
  public void test() {
    // given
    var userResponse = Mono.just(new UserResponse(1, "Alex"));

    // and
    when(repository.findById(1)).thenReturn(userResponse);

    // then
    testClient
        .get()
        .uri("/users/1")
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody(UserResponse.class)
        .value(
            response -> {
              Assertions.assertThat(response.id()).isEqualTo(1);
              Assertions.assertThat(response.name()).isEqualTo("Alex");
            });
  }
}
