package com.malex;

import com.malex.mapper.ObjectMapper;
import com.malex.model.base.Actor;
import com.malex.model.base.Client;
import com.malex.model.base.User;
import com.malex.model.dto.ActorDto;
import com.malex.model.dto.ClientRequest;
import com.malex.model.dto.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ApiService {

  private final ObjectMapper mapper;

  /*
   * Simple mapping
   */
  User userToUserDto(UserDto userDto) {
    log.info("User dto - {}", userDto);
    User user = mapper.dtoToModel(userDto);
    log.info("User - {}", user);
    return user;
  }

  /*
   * Additional parameter
   */
  Client requestToClient(ClientRequest request, long generatedId) {
    log.info("Request - {}, generated id - {}", request, generatedId);
    var client = mapper.mapToEntity(request, generatedId);
    log.info("Entity - {}", client);
    return client;
  }

  /*
   * Default values and constants using expression
   */
  Actor dtoToActor(ActorDto actorDto) {
    log.info("Actor dto - {}", actorDto);
    var actor = mapper.dtoToModel(actorDto);
    log.info("Actor - {}", actor);
    return actor;
  }
}
