package com.malex.controller;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SemaphoreRestControllerTest {

  private static final String RESPONSE_TEMPLATE = "Success - %s";

  @Autowired private TestRestTemplate restTemplate;

  @Test
  void testLimitedEndpoint() throws InterruptedException {
    // given
    var request = "hello:" + UUID.randomUUID();
    for (int i = 0; i < 1000; i++) {
      var thread =
          new Thread(
              () -> {

                // when
                var responseEntity =
                    restTemplate.getForEntity("/v1/rate-limit/semaphore/" + request, String.class);

                // then
                assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
                assertThat(responseEntity.getBody())
                    .isEqualTo(String.format(RESPONSE_TEMPLATE, request));
              });
      thread.start();
      thread.join();
    }
  }
}
