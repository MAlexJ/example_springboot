package com.malex.rest.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.malex.rest.service.WebService;
import org.junit.jupiter.api.Test;
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
class StatusRestControllerTest {

  private static final String BASE_URL = "/status%s";

  @Autowired private MockMvc mvc;

  @MockitoBean private WebService webService;

  @Test
  void http_200_Ok_StatusCode() throws Exception {
    mvc.perform(get(BASE_URL.formatted("/200/ok"))).andExpect(status().isOk());
  }

  @Test
  void http_400_badRequest_StatusCode() throws Exception {
    mvc.perform(get(BASE_URL.formatted("/400/badRequest"))).andExpect(status().isBadRequest());
  }

  @Test
  void http_404_notFound_StatusCode() throws Exception {
    mvc.perform(get(BASE_URL.formatted("/404/notFound"))).andExpect(status().isNotFound());
  }
}
