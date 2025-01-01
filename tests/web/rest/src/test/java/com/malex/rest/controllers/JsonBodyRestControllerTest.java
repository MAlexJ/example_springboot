package com.malex.rest.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
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
class JsonBodyRestControllerTest {

  private static final String BASE_URL = "/json/body";

  @Autowired private MockMvc mvc;

  @MockitoBean private WebService webService;

  @Test
  void testBodyParameters() throws Exception {
    mvc.perform(get(BASE_URL))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(1))
        .andExpect(jsonPath("$.name").value("Alex"))

        // empty field
        .andExpect(jsonPath("$.description").isEmpty())

        // doesNotExist
        .andExpect(jsonPath("$.email").doesNotExist());
  }
}
