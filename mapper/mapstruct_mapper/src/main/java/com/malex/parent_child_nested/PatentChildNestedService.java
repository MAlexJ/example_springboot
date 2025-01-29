package com.malex.parent_child_nested;

import static com.malex.MapstructMapperApplication.logDto;
import static com.malex.MapstructMapperApplication.logEntity;

import com.malex.parent_child_nested.dto.RecordDto;
import com.malex.parent_child_nested.entity.Record;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PatentChildNestedService {

  private final PatentChildNestedObjectMapper patentChildNestedObjectMapper;

  Record dtoToEntity(RecordDto dto) {
    var entity = patentChildNestedObjectMapper.dtoToEntity(logDto(dto));
    return logEntity(entity);
  }
}
