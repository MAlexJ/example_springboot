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

#### Additional parameter in mapstruct mapper

link: https://stackoverflow.com/questions/74865423/additional-parameter-in-mapstruct-mapper

```
    public record Client(Long id, String username){}

    public record ClientRequest(String username){}

    @Mapping(target = "id", source = "userId")
    Client requestToEntity(ClientRequest request, Long userId);
```

#### How can you specify a defaultValue when mapping a DTO using MapStruct?

##### Default values and constants

Default values can be specified to set predefined value to target property if the corresponding source property is null.
Constants can be specified to set such a predefined value in any case. Default values and constants are specified
as String values. When the target type is a primitive or a boxed type,
the String value is taken literal.
Bit / octal / decimal / hex patterns are allowed in such a case as long as they are a valid literal.
In all other cases, constant or default values are subject to type conversion either via built-in conversions
or the invocation of other mapping methods in order to match the type required by the target property.

link: https://mapstruct.org/documentation/stable/reference/html/#default-values-and-constants

##### 1. Option: Using expression

You can use expression for more complex logic to set the default value or to handle cases where the attribute is a
primitive type like boolean.

```
@Mapping(target = "used", expression = "java(false)")
MyDTO toDto(MyEntity entity);
```

##### 2. Option: Using defaultValue

To use defaultValue in your case, change the target field type to Boolean. This way MapStruct can use the provided
default value when the source value is null or absent.

```
@Mapping(target = "used", defaultValue = "false")
MyDTO toDto(MyEntity entity);
```

##### 3. Option: Using constant

If you want to set a fixed value regardless of the source is null or abset, you can constant.

```
@Mapping(target = "used", constant = "false")
MyDTO toDto(MyEntity entity);
```

link: https://stackoverflow.com/questions/59484557/how-to-specify-a-default-value-for-a-boolean-field-using-mapstruct