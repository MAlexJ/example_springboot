package com.malex.redis_data_store_for_cache.controller;

import com.malex.redis_data_store_for_cache.database.service.UserService;
import com.malex.redis_data_store_for_cache.rest.UserRestController;
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
public class UpdateUserRestControllerTest {

  private static final String UPDATE_USER_PATH = "/v1/users";

  @Autowired private MockMvc mvc;

  /*
   * Deprecated @MockBeans in SpringBoot 3.4.0
   * link: https://stackoverflow.com/questions/79243535/what-ist-the-replacement-for-deprecated-mockbeans-in-springboot-3-4-0
   */
  @MockitoBean private UserService userService;
}
