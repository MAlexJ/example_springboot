package com.malex.redis_data_store_for_cache.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.malex.redis_data_store_for_cache.database.service.UserService;
import com.malex.redis_data_store_for_cache.rest.UserRestController;
import com.malex.redis_data_store_for_cache.rest.response.UserResponse;
import java.time.LocalDateTime;
import java.util.Optional;
import org.hamcrest.Matchers;
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
class FindByIdUserRestControllerTest {

  private static final String FIND_USER_BY_ID_PATH = "/v1/users/{id}";

  @Autowired private MockMvc mvc;

  /*
   * Deprecated @MockBeans in SpringBoot 3.4.0
   * link: https://stackoverflow.com/questions/79243535/what-ist-the-replacement-for-deprecated-mockbeans-in-springboot-3-4-0
   */
  @MockitoBean private UserService userService;

  @Test
  void findUserById() throws Exception {
    // given
    var id = 1L;
    var username = "testUser";
    var created = LocalDateTime.now();

    // when
    Mockito.when(this.userService.findById(id))
        .thenReturn(Optional.of(new UserResponse(id, username, created)));

    // then
    mvc.perform(get(FIND_USER_BY_ID_PATH, id)) //
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(id))
        .andExpect(jsonPath("$.username").value(username));
  }

  @Test
  void findNotExistUserById() throws Exception {
    // given
    var id = 123L;

    // when
    Mockito.when(this.userService.findById(id)).thenReturn(Optional.empty());

    // then
    mvc.perform(get(FIND_USER_BY_ID_PATH, id)) //
        .andExpect(status().isNotFound())
        .andExpect(content().string(Matchers.blankOrNullString()));
  }
}
