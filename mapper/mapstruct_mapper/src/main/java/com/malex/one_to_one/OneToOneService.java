package com.malex.one_to_one;

import static com.malex.MapstructMapperApplication.logDto;
import static com.malex.MapstructMapperApplication.logEntity;

import com.malex.one_to_one.dto.ActorDto;
import com.malex.one_to_one.dto.ClientDto;
import com.malex.one_to_one.dto.UserDto;
import com.malex.one_to_one.entity.Actor;
import com.malex.one_to_one.entity.Client;
import com.malex.one_to_one.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OneToOneService {

  private final OneToOneObjectMapper oneToOneObjectMapper;

  User dtoToEntity(UserDto dto) {
    var user = oneToOneObjectMapper.dtoToEntity(logDto(dto));
    return logEntity(user);
  }

  Actor dtoToEntity(ActorDto dto) {
    var actor = oneToOneObjectMapper.dtoToEntity(logEntity(dto));
    return logEntity(actor);
  }

  Client dtoToEntity(ClientDto dto, Long generatedId) {
    var client = oneToOneObjectMapper.dtoToEntity(logDto(dto), generatedId);
    return logEntity(client);
  }
}
