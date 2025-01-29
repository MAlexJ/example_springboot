package com.malex.patent_child_nested_one_to_many;

import static com.malex.MapstructMapperApplication.logDto;
import static com.malex.MapstructMapperApplication.logEntity;

import com.malex.patent_child_nested_one_to_many.dto.SingleDTO;
import com.malex.patent_child_nested_one_to_many.entity.ParentEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ParentChildNestedOneToManyService {

  private final ParentChildNestedOneToManyObjectMapper parentChildNestedOneToManyObjectMapper;

  ParentEntity dtoToEntity(SingleDTO dto) {
    var entityParent = parentChildNestedOneToManyObjectMapper.toLoan(logDto(dto));
    return logEntity(entityParent);
  }
}
