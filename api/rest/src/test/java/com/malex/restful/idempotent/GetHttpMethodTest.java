package com.malex.restful.idempotent;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.malex.restful.model.User;
import com.malex.restful.repository.UserRepository;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

/*
 * Use GET requests to retrieve resource representation/information only – and not modify it in any way.
 *
 * As GET requests do not change the resource’s state, these are said to be safe methods.
 *
 * Additionally, GET APIs should be idempotent. Making multiple identical requests must produce the same result
 * every time until another API (POST or PUT) has changed the state of the resource on the server.
 *
 */
@WebMvcTest
class GetHttpMethodTest {

  @Autowired private MockMvc mvc;

  @MockitoBean(name = "userRepository")
  private UserRepository repository;

  // WTF!
  void idempotency() {}

  /*
  *  GET API Response Codes:
  *
   - 200 (OK): For any given HTTP GET API, if the resource is found on the server, then it must return HTTP response
   code 200 (OK) along with the response body, which is usually either XML or JSON content.

   - 404 (NOT FOUND): In case the resource is NOT found on the server then API must
   return HTTP response code 404 (NOT FOUND).

   - 400 (BAD REQUEST): Similarly, if it is determined that the GET request itself is not correctly formed then
    the server will return the HTTP response code 400 (BAD REQUEST).
  *
  */
  @Test
  void http200OkStatusCode() throws Exception {
    // given
    var users =
        List.of(
            new User(1L, "Anna", LocalDateTime.now()), //
            new User(2L, "Stefan", LocalDateTime.now()));

    // when
    when(repository.findAll()).thenReturn(users);

    // first call
    mvc.perform(get("/api/v1/users"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.size()").value(2));

    // second call
    mvc.perform(get("/api/v1/users"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.size()").value(2));
  }

  /*
   * So what's the difference between 400 and 404 errors?
   *
   * The 404 errors represent resources not existing, and in error 400, the resource exists, but the input is wrong
   */
  @Test
  void http404BadRequestStatusCode() throws Exception {
    mvc.perform(get("/api/v2/dogs")).andExpect(status().isNotFound());
  }
}
