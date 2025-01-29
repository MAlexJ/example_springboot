package com.malex.patent_child_nested_one_to_many;

import com.malex.patent_child_nested_one_to_many.dto.SingleDTO;
import com.malex.patent_child_nested_one_to_many.entity.ParentEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/** MapStruct mapper: */
@Mapper(componentModel = "spring")
public interface ParentChildNestedOneToManyObjectMapper {

  /*
   * MapStruct Child Entity
   */
  @Mapping(target = "total.amount", source = "totalAmount")
  @Mapping(target = "total.currency", source = "currency")
  @Mapping(target = "paid.amount", source = "paidAmount")
  @Mapping(target = "paid.currency", source = "currency")
  ParentEntity toLoan(SingleDTO dto);
}
