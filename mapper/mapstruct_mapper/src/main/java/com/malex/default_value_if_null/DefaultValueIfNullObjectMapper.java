package com.malex.default_value_if_null;

import com.malex.default_value_if_null.dto.TasksRequest;
import com.malex.default_value_if_null.entity.TaskEntity;
import org.mapstruct.Mapper;

/** MapStruct mapper: */
@Mapper(componentModel = "spring")
public interface DefaultValueIfNullObjectMapper {

  TaskEntity defaultValueIfNull(TasksRequest request);
}
