package com.malex.redis_data_store_for_cache.controller;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.malex.redis_data_store_for_cache.database.service.UserService;
import com.malex.redis_data_store_for_cache.rest.UserRestController;
import com.malex.redis_data_store_for_cache.rest.response.DeleteUserResponse;
import com.malex.redis_data_store_for_cache.rest.response.FindUserByIdResponse;
import com.malex.redis_data_store_for_cache.rest.response.FindUsersResponse;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

/*
 * Tests web controllers
 *
 * <p>link: <a href="https://habr.com/ru/companies/otus/articles/746414/">Tests REST controllers</a>
 *
 * @WebMvcTest - Annotation that can be used for a Spring MVC test that focuses only on Spring MVC components.
 * Using this annotation will disable full auto-configuration and instead apply only configuration
 * relevant to MVC tests (i. e. @Controller, @ControllerAdvice, @JsonComponent, Converter/GenericConverter,
 * Filter, WebMvcConfigurer and HandlerMethodArgumentResolver beans
 * but not @Component, @Service or @Repository beans) !!!
 *
 */
@WebMvcTest(UserRestController.class)
class UserRestControllerTest {

  private static final String USERS_PATH = "/v1/users";
  private static final String FIND_USER_BY_ID_PATH = "/v1/users/{id}";

  @Autowired private MockMvc mvc;

  /*
   * Deprecated @MockBeans in SpringBoot 3.4.0
   * link: https://stackoverflow.com/questions/79243535/what-ist-the-replacement-for-deprecated-mockbeans-in-springboot-3-4-0
   */
  @MockitoBean private UserService userService;

  @Test
  void findAllUsers() throws Exception {
    var firstId = 1;
    var firstUsername = "test user 1";
    var firstCreated = LocalDateTime.now();
    var firstUser =
        new FindUserByIdResponse(Integer.toUnsignedLong(firstId), firstUsername, firstCreated);

    var secondId = 2;
    var secondUsername = "test user 2";
    var secondCreated = LocalDateTime.now().minusDays(1);
    var secondUser =
        new FindUserByIdResponse(Integer.toUnsignedLong(secondId), secondUsername, secondCreated);

    Mockito.when(this.userService.findAll())
        .thenReturn(new FindUsersResponse(List.of(firstUser, secondUser)));

    mvc.perform(get(USERS_PATH)) //
        .andExpect(status().isOk())

        /*
         * Spring MockMvc: match a collection of JSON objects in any order
         * link: https://stackoverflow.com/questions/55269036/spring-mockmvc-match-a-collection-of-json-objects-in-any-order
         *
         * Testing JSON in Spring Boot
         * link: https://mkyong.com/spring-boot/testing-json-in-spring-boot/
         */
        .andExpect(jsonPath("$.users.[*].id", containsInAnyOrder(firstId, secondId)))
        .andExpect(
            jsonPath("$.users.[*].username", containsInAnyOrder(firstUsername, secondUsername)))
        //        .andExpect(
        //            jsonPath(
        //                "$[*].created",
        //                containsInAnyOrder(firstCreated.toString(), secondCreated.toString())))
        .andExpect(jsonPath("$.size", is(2)));
  }

  @DisplayName("verify status code and response of /v1/users endpoint when users are empty")
  @Test
  void findAllUsersEmptyCollection() throws Exception {
    Mockito.when(this.userService.findAll())
        .thenReturn(new FindUsersResponse(Collections.emptyList()));

    mvc.perform(get(USERS_PATH)) //
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.size", is(0)));
  }

  @Test
  void findUserById() throws Exception {
    var id = 1L;
    var username = "testUser";
    var created = LocalDateTime.now();

    Mockito.when(this.userService.findById(id))
        .thenReturn(Optional.of(new FindUserByIdResponse(id, username, created)));

    mvc.perform(get(FIND_USER_BY_ID_PATH, id)) //
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(id))
        .andExpect(jsonPath("$.username").value(username));
  }

  @Test
  void findNotExistUserById() throws Exception {
    var id = -1L;

    Mockito.when(this.userService.findById(id)).thenReturn(Optional.empty());

    mvc.perform(get(FIND_USER_BY_ID_PATH, id)) //
        .andExpect(status().isNotFound())
        .andExpect(content().string(Matchers.blankOrNullString()));
  }

  @Test
  void deleteUserById() throws Exception {
    var id = 1;

    Mockito.when(this.userService.delete(Integer.toUnsignedLong(id)))
        .thenReturn(
            Optional.of(
                new DeleteUserResponse(Integer.toUnsignedLong(id), "test", LocalDateTime.now())));

    mvc.perform(delete(FIND_USER_BY_ID_PATH, id)) //
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id", is(id)))
        .andExpect(jsonPath("$.username", is("test")));
  }

  @Test
  void deleteNotExistUserById() throws Exception {
    var id = -1L;

    Mockito.when(this.userService.delete(id)).thenReturn(Optional.empty());

    mvc.perform(delete(FIND_USER_BY_ID_PATH, id)) //
        .andExpect(status().isNotFound())
        .andExpect(content().string(Matchers.blankOrNullString()));
  }
}
