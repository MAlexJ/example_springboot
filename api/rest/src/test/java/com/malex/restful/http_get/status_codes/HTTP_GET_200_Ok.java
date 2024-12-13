package com.malex.restful.http_get.status_codes;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.malex.restful.model.User;
import com.malex.restful.repository.UserRepository;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

/*
 * 200 (OK)
 *
 * For any given HTTP GET API, if the resource is found on the server, then it must return HTTP response
 * code 200 (OK) along with the response body, which is usually either XML or JSON content.
 */
@WebMvcTest
class HTTP_GET_200_Ok {

  @Autowired private MockMvc mvc;

  @MockitoBean(name = "userRepository")
  private UserRepository repository;

  @Test
  void http200Ok_find_all() throws Exception {
    // given
    var users =
        List.of(
            new User(1L, "Anna", LocalDateTime.now()), //
            new User(2L, "Stefan", LocalDateTime.now()));

    // when
    when(repository.findAll()).thenReturn(users);

    // then
    mvc.perform(get("/api/v1/users"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.total").value(2));
  }

  /*
   * HTTP 200 back with an empty list.
   *
   * link: https://medium.com/nerd-for-tech/navigating-http-status-codes-for-rest-apis-39f25fcd8cc6
   *
   * If the client calls GET /users?name=joy, then the client is asking for a list of all users with the name joy.
   * In this case, the URI, /users does exist and is valid.
   * If there is no one with the name joy, then we’ve just filtered that list down to nothing
   * and the client should be getting a 200 back with an empty list.
   */
  @Test
  void http200Ok_filtering_empty_list() throws Exception {
    // given
    var name = "joy";
    var path = "/api/v1/users?name=%s".formatted(name);

    // when
    when(repository.findAll("joy")).thenReturn(Collections.emptyList());

    // then
    mvc.perform(get(path)) //
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.users").isEmpty())
        .andExpect(jsonPath("$.total").value(0));
  }
}
