package com.malex.default_value_if_null;

import com.malex.default_value_if_null.dto.TasksRequest;
import com.malex.default_value_if_null.entity.TaskEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DefaultValueIfNullService {

  private final DefaultValueIfNullObjectMapper defaultValueIfNullObjectMapper;

  public TaskEntity defaultValueIfNull(TasksRequest request) {
    return defaultValueIfNullObjectMapper.defaultValueIfNull(request);
  }
}
