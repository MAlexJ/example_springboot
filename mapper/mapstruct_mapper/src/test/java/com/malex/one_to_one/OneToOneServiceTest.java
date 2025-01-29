package com.malex.one_to_one;

import static org.junit.jupiter.api.Assertions.*;

import com.malex.one_to_one.dto.ActorDto;
import com.malex.one_to_one.dto.ClientDto;
import com.malex.one_to_one.dto.UserDto;
import java.util.Random;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class OneToOneServiceTest {

  @Autowired private OneToOneService oneToOneService;

  @Test
  void testUser() {
    // given
    var id = 1;
    var name = UUID.randomUUID().toString();
    var age = 18;

    // when
    var userEntity = oneToOneService.dtoToEntity(new UserDto(id, name, age));

    // then
    assertEquals(id, userEntity.getId());
    assertEquals(name, userEntity.getName());
    assertEquals(age, userEntity.getAge());
  }

  @Test
  void testActor() {
    // given
    var name = UUID.randomUUID().toString();

    // when
    var actorEntity = oneToOneService.dtoToEntity(new ActorDto(name));

    // then
    assertNotNull(actorEntity);
    assertEquals(name, actorEntity.name());
  }

  @Test
  void testClient() {
    // given
    var name = UUID.randomUUID().toString();
    var generatedId = new Random().nextLong();

    // when
    var clientEntity = oneToOneService.dtoToEntity(new ClientDto(name), generatedId);

    // then
    assertEquals(name, clientEntity.name());
    assertEquals(generatedId, clientEntity.id());
  }
}
