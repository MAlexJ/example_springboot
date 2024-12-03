package com.malex;

import static org.junit.jupiter.api.Assertions.*;

import com.malex.model.base.User;
import com.malex.model.dto.UserDto;
import java.util.Random;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ApiServiceTest {

  private final Random random = new Random();

  @Autowired private ApiService apiService;

  @Test
  void testUser() {
    // given
    UserDto userDto = UserDto.builder().build();

    // and
    Integer age = 10;
    userDto.setAge(age);

    // and
    Integer id = random.nextInt();
    userDto.setId(id);

    // and
    String name = UUID.randomUUID().toString();
    userDto.setName(name);

    // when
    User user = apiService.userToUserDto(userDto);

    // then
    assertNotNull(user);
    assertEquals(age, user.getAge());
    assertEquals(id, user.getId());
    assertEquals(name, user.getName());
  }
}
