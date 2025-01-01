package com.malex;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MockMvc;

/*
 * Media Types
 *
 * If we would like to define the structure of the resource that we are rendering or consuming,
 * we can use media type to define.
 * It also helps in versioning the API.
 *
 * consumes - matched only if the Content-Type request header matches
 *
 * produces - matched only if the Accept request header matches
 */
@WebMvcTest
class MimeTypeVersioningAppTest {

  @Autowired private MockMvc mvc;

  @Test
  void v1_default_test() throws Exception {
    mvc.perform(get("/students"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(1))
        .andExpect(jsonPath("$.name").value("Stefan"))
        .andExpect(jsonPath("$.email").value("email"));
  }

  @Test
  void v1_with_headers_test() throws Exception {
    // given
    var acceptHeader = "application/vnd.amitph.students.v1+json";

    // when
    mvc.perform(get("/students").header(HttpHeaders.ACCEPT, acceptHeader))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(1))
        .andExpect(jsonPath("$.name").value("Stefan"))
        .andExpect(jsonPath("$.email").value("email"));
  }

  /*
   * Get students v2
   * GET http://localhost:8080/students
   * Accept: application/vnd.amitph.students.v2+json
   */
  @Test
  void v2_with_headers_test() throws Exception {
    // given
    var acceptHeader = "application/vnd.amitph.students.v2+json";

    // when
    mvc.perform(get("/students").header(HttpHeaders.ACCEPT, acceptHeader))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.name").value("Stefan"))

        // then
        .andExpect(jsonPath("$.id").value(2))

        // amd
        .andExpect(jsonPath("$.email").doesNotExist());
  }
}
