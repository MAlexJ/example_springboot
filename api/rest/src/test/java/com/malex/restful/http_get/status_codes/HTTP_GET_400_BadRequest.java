package com.malex.restful.http_get.status_codes;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.malex.restful.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

/*
 * 400 (BAD REQUEST):
 * Similarly, if it is determined that the GET request itself is not correctly formed then
 * the server will return the HTTP response code 400 (BAD REQUEST).
 */
@WebMvcTest
class HTTP_GET_400_BadRequest {

  @Autowired private MockMvc mvc;

  @MockitoBean(name = "userRepository")
  private UserRepository repository;

  /*
   * Case 1: invalid request - id parameter
   *
   * If the client sent a garbled request (invalid param, invalid URI format, etc), then they should get back a 400.
   *
   * link: https://medium.com/nerd-for-tech/navigating-http-status-codes-for-rest-apis-39f25fcd8cc6
   */
  @Test
  void http400_bad_request() throws Exception {
    mvc.perform(get("/api/v1/users/-1")).andExpect(status().isBadRequest());
  }
}
