package com.malex;

import com.malex.model.dto.ActorDto;
import com.malex.model.dto.ClientRequest;
import com.malex.model.dto.UserDto;
import java.util.Random;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
@RequiredArgsConstructor
public class MapstructMapperApplication implements CommandLineRunner {

  private final ApiService service;

  private final Random random = new Random();

  public static void main(String[] args) {
    SpringApplication.run(MapstructMapperApplication.class, args);
  }

  @Override
  public void run(String... args) {
    // simple
    var userDto = UserDto.builder().id(1).age(15).name("Alex").build();
    service.userToUserDto(userDto);

    // additional parameter
    long generatedId = random.nextLong();
    service.requestToClient(new ClientRequest("New name"), generatedId);

    // Default values and constants using expression
    service.dtoToActor(new ActorDto("ActorB"));
  }
}
