### MapStruct

MapStruct is a code generator that greatly simplifies the implementation of mappings between Java bean types based on a
convention over configuration approach.

link: https://mapstruct.org

#### Simple springboot configuration

```
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/** MapStruct mapper: */
@Mapper(componentModel = "spring")
public interface ObjectMapper {

  @Mapping(target = "id", ignore = true)
  Book requestToEntity(BookDtoRequest request);

  BookDtoResponse entityToResponse(Book entity);
}

```

link: https://www.baeldung.com/mapstruct
link: https://habr.com/ru/articles/818489/