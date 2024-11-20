package com.malex;

import com.malex.mapper.ObjectMapper;
import com.malex.model.base.User;
import com.malex.model.dto.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ApiService {

  private final ObjectMapper mapper;

  User userToUserDto(UserDto userDto) {
    log.info("Dto - {}", userDto);
    User user = mapper.dtoToModel(userDto);
    log.info("Model - {}", user);
    return user;
  }
}
