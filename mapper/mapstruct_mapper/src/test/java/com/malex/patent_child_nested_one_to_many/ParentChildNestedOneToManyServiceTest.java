package com.malex.patent_child_nested_one_to_many;

import static org.junit.jupiter.api.Assertions.*;

import com.malex.patent_child_nested_one_to_many.dto.SingleDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ParentChildNestedOneToManyServiceTest {

  @Autowired private ParentChildNestedOneToManyService parentChildNestedOneToManyService;

  @Test
  void test() {
    // given
    var paidAmount = -1L;
    var totalAmount = 2L;
    var currency = "USD";
    var dto = new SingleDTO(paidAmount, totalAmount, currency);

    // when
    var entityParent = parentChildNestedOneToManyService.dtoToEntity(dto);

    var paid = entityParent.paid();

    var totalEntityChild = entityParent.total();

    // then:
    // @Mapping(target = "paid.amount", source = "paidAmount")
    // @Mapping(target = "paid.currency", source = "currency")
    assertEquals(paidAmount, paid.amount());
    assertEquals(currency, paid.currency());

    // and
    // @Mapping(target = "total.amount", source = "totalAmount")
    // @Mapping(target = "total.currency", source = "currency")
    assertEquals(totalAmount, totalEntityChild.amount());
    assertEquals(currency, totalEntityChild.currency());
  }
}
