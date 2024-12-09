package com.malex.redis_data_store_for_cache.database.mapper;

import com.malex.redis_data_store_for_cache.database.entity.UserEntity;
import com.malex.redis_data_store_for_cache.rest.request.UserRequest;
import com.malex.redis_data_store_for_cache.rest.response.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/** MapStruct mapper: */
@Mapper(componentModel = "spring")
public interface ObjectMapper {

  UserResponse entityToResponse(UserEntity entity);

  UserEntity requestToEntity(UserRequest userRequest);

  /*
   * Additional parameter in mapstruct mapper
   *
   * link: https://stackoverflow.com/questions/74865423/additional-parameter-in-mapstruct-mapper
   */
  @Mapping(target = "id", source = "userId")
  @Mapping(target = "created", expression = "java(LocalDateTime.now())")
  UserEntity requestToEntity(Long userId, UserRequest userRequest);
}
