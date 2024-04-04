package com.malexj.rest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.malexj.controller.SignatureRestController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Tests web controllers
 *
 * <p>link: <a href="https://habr.com/ru/companies/otus/articles/746414/">Tests REST controllers</a>
 */
@WebMvcTest(SignatureRestController.class)
public class SpringBootApplicationIntegrationTest {

  private static final String SIGNATURE_URL = "/v1/signature";

  @Autowired private MockMvc mockMvc;

  @Test
  void getSignature() throws Exception {
    mockMvc
        .perform(get(SIGNATURE_URL)) //
        .andExpect(status().isOk());
  }
}
