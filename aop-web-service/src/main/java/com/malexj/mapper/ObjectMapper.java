package com.malexj.mapper;

import com.malexj.entity.Book;
import com.malexj.model.request.BookDtoRequest;
import com.malexj.model.response.BookDtoResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/** MapStruct mapper: */
@Mapper(componentModel = "spring")
public interface ObjectMapper {

  @Mapping(target = "id", ignore = true)
  Book requestToEntity(BookDtoRequest request);

  BookDtoResponse entityToResponse(Book entity);
}
