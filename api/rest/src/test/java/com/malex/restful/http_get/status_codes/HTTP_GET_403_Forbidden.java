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
 * 403 (Forbidden):
 *  error response indicates that the client’s request is formed correctly, but the REST API refuses to honor it, i.e.,
 * the user does not have the necessary permissions for the resource.
 *
 * A 403 response is not a case of insufficient client credentials; that would be 401 (“Unauthorized”).
 *
 * Authentication will not help, and the request SHOULD NOT be repeated.
 * Unlike a 401 Unauthorized response, authenticating will make no difference.
 *
 * The big difference between a 403 and a 401 is that with a 401,
 * I cannot access any endpoint successfully. With a 403, I just can’t access this particular one.
 */
@WebMvcTest
class HTTP_GET_403_Forbidden {

  @Autowired private MockMvc mvc;

  @MockitoBean(name = "userRepository")
  private UserRepository repository;

  /*
   * case 1: User hasn't permissions to admin endpoint
   */
  @Test
  void http403_Forbidden_no_permissions() throws Exception {

    // given
    var path = "/api/v1/auth-endpoint/clients";

    // then
    mvc.perform(get(path).header(HttpHeaders.AUTHORIZATION, "Basic NOT_ADMIN"))
        .andExpect(status().isForbidden());
  }
}
