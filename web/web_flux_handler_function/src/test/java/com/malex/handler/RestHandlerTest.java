package com.malex.handler;

import static org.mockito.Mockito.when;

import com.malex.model.dto.UserResponse;
import com.malex.repository.CrudRepository;
import com.malex.routing.RestRouterFunction;
import java.net.URI;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

/*
 * Spring-Webflux: Testing your Router Functions with WebTestClient
 *
 * link to test: https://blog.knoldus.com/spring-webflux-testing-your-router-functions-with-webtestclient/
 *
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
  public void findUserById() {
    // given
    var userId = 1;
    var userResponse = Mono.just(new UserResponse(userId, "Alex"));

    // and
    when(repository.findById(1)).thenReturn(userResponse);

    // then
    testClient
        .get()
        .uri(buildUri(userId))
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody(UserResponse.class)
        .value(
            response -> {
              Assertions.assertThat(response.id()).isEqualTo(userId);
              Assertions.assertThat(response.name()).isEqualTo("Alex");
            });
  }

  @Test
  public void findNotFoundUserById() {
    // given
    var userId = 2;
    // and
    when(repository.findById(userId)).thenReturn(Mono.empty());

    // then
    testClient.get().uri(buildUri(userId)).exchange().expectStatus().isNotFound();
  }

  /*
   * UriComponentsBuilder
   *
   * link to tutorial: https://javarush.com/quests/lectures/questspring.level05.lecture04
   */
  private URI buildUri(Integer id) {
    return UriComponentsBuilder.fromUriString("/users/{id}").build(id);
  }
}
