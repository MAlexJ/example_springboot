package com.malex.http_delete.idenpotency;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.malex.model.User;
import com.malex.repository.UserRepository;
import java.time.LocalDateTime;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

/*
 * DELETE operations are idempotent.
 * If you DELETE a resource, it’s removed from the collection of resources.
 * Some may argue that it makes the DELETE method non-idempotent.
 * It’s a matter of discussion and personal opinion.
 *
 * Repeatedly calling DELETE API on that resource will not change the outcome – however,
 * calling DELETE on a resource a second time will return a 404 (NOT FOUND) since it was already removed.
 */
@WebMvcTest
public class HTTP_DELETE_200_OR_404_Idempotency {

  @Autowired private MockMvc mvc;

  @MockitoBean(name = "userRepository")
  private UserRepository repository;

  @Test
  public void idempotency() throws Exception {

    // given
    var userId = 1L;
    var mockUsers = new User(1L, "test", LocalDateTime.now());

    // when
    when(repository.deleteById(1L)).thenReturn(Optional.of(mockUsers));

    // then
    mvc.perform(delete("/api/v1/users/" + userId)).andExpect(status().isOk());
  }
}
