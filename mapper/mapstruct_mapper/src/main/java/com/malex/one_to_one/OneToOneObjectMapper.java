package com.malex.one_to_one;

import com.malex.one_to_one.dto.ActorDto;
import com.malex.one_to_one.dto.ClientDto;
import com.malex.one_to_one.dto.UserDto;
import com.malex.one_to_one.entity.Actor;
import com.malex.one_to_one.entity.Client;
import com.malex.one_to_one.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/** MapStruct mapper: */
@Mapper(componentModel = "spring")
public interface OneToOneObjectMapper {

  /*
   * Additional parameter in mapstruct mapper
   *
   * id - from Client class
   * generatedId - additional parameter
   */
  @Mapping(target = "id", source = "generatedId")
  Client dtoToEntity(ClientDto dto0, Long generatedId);

  /*
   * Default values and constants using expression
   */
  @Mapping(target = "created", expression = "java(LocalDateTime.now())")
  Actor dtoToEntity(ActorDto dto);

  /*
   * Simple mapping
   */
  User dtoToEntity(UserDto dto);
}
