package com.malex.parent_child_nested;

import static org.junit.jupiter.api.Assertions.*;

import com.malex.parent_child_nested.dto.FieldDto;
import com.malex.parent_child_nested.dto.RecordDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PatentChildNestedServiceTest {

  @Autowired private PatentChildNestedService patentChildNestedService;

  @Test
  void test() {
    // given
    var fieldName = "new record";
    var fieldDto = new FieldDto(fieldName);

    // and
    var id = 1;
    var name = "Test";
    var recordDto = new RecordDto(id, name, fieldDto);

    // when
    var recordEntity = patentChildNestedService.dtoToEntity(recordDto);
    var fieldEntity = recordEntity.field();

    // then
    assertEquals(id, recordEntity.id());
    assertEquals(name, recordEntity.name());
    assertEquals(fieldName, recordEntity.field().fieldName());

    // and
    assertEquals(fieldName, fieldEntity.fieldName());
  }
}
