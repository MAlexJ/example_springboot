package com.malex.http_delete.status_codes;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.malex.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest
public class HTTP_DELETE_422_UNPROCESSABLE_ENTITY {

  @Autowired private MockMvc mvc;

  @MockitoBean(name = "userRepository")
  private UserRepository repository;

  /*
   * Case 1: invalid request - id parameter
   *
   * If the client sent a garbled request (invalid param, invalid URI format, etc), then they should get back a 422.
   *
   * link: https://medium.com/nerd-for-tech/navigating-http-status-codes-for-rest-apis-39f25fcd8cc6
   */
  @Test
  void http422_unprocessable_entity_validation_error() throws Exception {
    mvc.perform(delete("/api/v1/users/-1")).andExpect(status().isUnprocessableEntity());
  }
}
