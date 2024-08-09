package com.malex.request_validation.controller;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.malex.request_validation.exception.AppExceptionHandler;
import com.malex.request_validation.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest
@PropertySource("classpath:application.yaml")
@ContextConfiguration(classes = {RestApiController.class, AppExceptionHandler.class})
class RestApiControllerTest {

  private static final String USERS_PATH = "/v1/users";

  @Autowired private MockMvc mockMvc;

  @Autowired private ObjectMapper objectMapper;

  @Test
  void createUser() throws Exception {
    // given
    var user = User.builder().id(1).age(18).name("Test").email("email@email.com").build();

    // then
    mockMvc
        .perform(
            post(USERS_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user))) //
        .andExpect(status().isOk());
  }

  @Test
  void createUserWithoutName() throws Exception {
    // given
    var user = User.builder().id(1).age(18).email("email@email.com").build();

    // then
    mockMvc
        .perform(
            post(USERS_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user))) //
        .andExpect(status().isBadRequest())
        .andExpect(status().reason(containsString("Invalid request content.")));
  }

  @Test
  void createUserWithoutId() throws Exception {
    // given
    var user = User.builder().name("xxx").age(18).email("email@email.com").build();

    // then
    mockMvc
        .perform(
            post(USERS_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user))) //
        .andExpect(status().isBadRequest())
        .andExpect(status().reason(containsString("Invalid request content.")));
  }

  @Test
  void createUserWithoutEmail() throws Exception {
    // given
    var user = User.builder().id(2).name("Alex").age(18).build();

    // then
    mockMvc
        .perform(
            post(USERS_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user))) //
        .andExpect(status().isBadRequest())
        .andExpect(status().reason(containsString("Invalid request content.")));
  }
}
