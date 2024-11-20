package com.malex.mapper;

import com.malex.model.base.User;
import com.malex.model.dto.UserDto;
import org.mapstruct.Mapper;

/** MapStruct mapper: */
@Mapper(componentModel = "spring")
public interface ObjectMapper {

  User dtoToModel(UserDto dto);

  UserDto modelToDto(User model);
}
