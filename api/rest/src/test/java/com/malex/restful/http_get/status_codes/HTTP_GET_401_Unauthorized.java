package com.malex.restful.http_get.status_codes;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.malex.restful.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

/*
 * 401 (Unauthorized): Indicates that the request requires user authentication information.
 * The client MAY repeat the request with a suitable Authorization header field
 */
@WebMvcTest
class HTTP_GET_401_Unauthorized {

  @Autowired private MockMvc mvc;

  @MockitoBean(name = "userRepository")
  private UserRepository repository;

  /*
   * Case 1: Requires without authentication information/headers
   *
   * expected: 401 (Unauthorized) HTTP status code
   */
  @Test
  void http401_unauthorized_without_headers() throws Exception {

    // given
    var path = "/api/v1/auth-endpoint/clients";

    // then
    mvc.perform(get(path)).andExpect(status().isUnauthorized());
  }

  /*
   * Case 2: Wrong authentication, no permissions to resource
   *
   * expected: 401 (Unauthorized) HTTP status code
   *
   * If the request format was valid, but they didn’t provide valid authentication
   * (expired or invalid API Key), then they should get back a 401 (Unauthorized).
   *
   * A 401 (Unauthorized)  means that the client, with those credentials,
   * cannot make a successful call to any endpoints (that require authentication).
   */
  @Test
  void http401_unauthorized_no_permissions() throws Exception {

    // given
    var path = "/api/v1/auth-endpoint/clients";

    // then
    mvc.perform(get(path).header(HttpHeaders.AUTHORIZATION, "WRONG CREDENTIAL"))
        .andExpect(status().isUnauthorized());
  }
}
