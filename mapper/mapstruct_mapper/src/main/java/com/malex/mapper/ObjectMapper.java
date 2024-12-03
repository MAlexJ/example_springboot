package com.malex.mapper;

import com.malex.model.base.Actor;
import com.malex.model.base.Client;
import com.malex.model.base.User;
import com.malex.model.dto.ActorDto;
import com.malex.model.dto.ClientRequest;
import com.malex.model.dto.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/** MapStruct mapper: */
@Mapper(componentModel = "spring")
public interface ObjectMapper {

  /*
   * Simple mapping
   */
  User dtoToModel(UserDto dto);

  UserDto modelToDto(User model);

  /*
   * Additional parameter in mapstruct mapper
   *
   * id - from Client class
   * generatedId - additional parameter
   */
  @Mapping(target = "id", source = "generatedId")
  Client mapToEntity(ClientRequest request, Long generatedId);

  /*
   * Default values and constants using expression
   */
  @Mapping(target = "created", expression = "java(LocalDateTime.now())")
  Actor dtoToModel(ActorDto dto);
}
