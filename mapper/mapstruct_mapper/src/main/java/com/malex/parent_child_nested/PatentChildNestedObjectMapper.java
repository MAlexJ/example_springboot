package com.malex.parent_child_nested;

import com.malex.parent_child_nested.dto.RecordDto;
import com.malex.parent_child_nested.entity.Record;
import org.mapstruct.Mapper;

/** MapStruct mapper: */
@Mapper(componentModel = "spring")
public interface PatentChildNestedObjectMapper {

  Record dtoToEntity(RecordDto dto);
}
