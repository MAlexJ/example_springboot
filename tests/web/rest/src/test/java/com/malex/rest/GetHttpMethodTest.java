package com.malex.rest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

/*
*
* * Use GET requests to retrieve resource representation/information only – and not modify it in any way.
   * As GET requests do not change the resource’s state, these are said to be safe methods.
   * Additionally, GET APIs should be idempotent. Making multiple identical requests must produce the same result
   * every time until another API (POST or PUT) has changed the state of the resource on the server.
   *
   *
* Tests web controllers
*
* <p>link: <a href="https://habr.com/ru/companies/otus/articles/746414/">Tests REST controllers</a>
*
* @WebMvcTest - Annotation that can be used for a Spring MVC test that focuses only on Spring MVC components.
* Using this annotation will disable full auto-configuration and instead apply only configuration
* relevant to MVC tests:
   - @Controller,
   - @ControllerAdvice,
   - @JsonComponent,
   - Converter/GenericConverter
   - Filter
   - WebMvcConfigurer
   - HandlerMethodArgumentResolver beans
* but not @Component, @Service or @Repository beans) !!!
*
*/
@WebMvcTest
public class GetHttpMethodTest {

  @Autowired private MockMvc mvc;

  //    @MockitoBean(name = "userRepository")
  //    private UserRepository repository;

  /*
   * Use GET requests to retrieve resource representation/information only – and not modify it in any way.
   * As GET requests do not change the resource’s state, these are said to be safe methods.
   * Additionally, GET APIs should be idempotent. Making multiple identical requests must produce the same result
   * every time until another API (POST or PUT) has changed the state of the resource on the server.
   */
  //    @Test
  void httpResponseStatusCode() throws Exception {
    //        when(repository.findAll()).thenReturn(List.of(new User(1L, "Anna"), new User(2L,
    // "Stefan")));

    // first call
    mvc.perform(get("/api/v1/users"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.size()").value(2));

    // second call
    mvc.perform(get("/api/v1/users"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.size()").value(2));
  }
}
