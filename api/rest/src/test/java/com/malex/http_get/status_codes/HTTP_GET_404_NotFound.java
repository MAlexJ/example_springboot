package com.malex.http_get.status_codes;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.malex.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

/*
 * Use GET requests to retrieve resource representation/information only – and not modify it in any way.
 *
 * Save:
 * An HTTP method is safe if it doesn't alter the state of the server.
 * In other words, a method is safe if it leads to a read-only operation.
 * Several common HTTP methods are safe: GET, HEAD, or OPTIONS. All safe methods are also idempotent,
 * but not all idempotent methods are safe. For example, PUT and DELETE are both idempotent but unsafe.
 *
 * Idempotency:
 * As GET requests do not change the resource’s state, these are said to be safe methods.
 * Additionally, GET APIs should be idempotent. Making multiple identical requests must produce the same result
 * every time until another API (POST or PUT) has changed the state of the resource on the server.
 *
 */
@WebMvcTest
class HTTP_GET_404_NotFound {

  @Autowired private MockMvc mvc;

  @MockitoBean(name = "userRepository")
  private UserRepository repository;

  /*
   * Case 1: resources not existing in db
   *
   * If the client calls GET /users/{userId}, then the client is specifying that specific userId as a part of the URI.
   * If that userId does not exist from the client’s perspective, the client should get a 404 (invalid URI).
   *
   * link: https://medium.com/nerd-for-tech/navigating-http-status-codes-for-rest-apis-39f25fcd8cc6
   */
  @Test
  void http404_NotFound_resource_by_id_not_exist() throws Exception {
    mvc.perform(get("/api/v1/users/478")).andExpect(status().isNotFound());
  }

  /*
   *  Case 2: url for resource not existing
   *
   * So what's the difference between 400 and 404 errors?
   * The 404 errors represent resources not existing, and in error 400, the resource exists, but the input is wrong
   */
  @Test
  void http404_NotFound_url_not_found() throws Exception {
    mvc.perform(get("/dogs")).andExpect(status().isNotFound());
  }
}
