package com.malexj.model.request;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class BookDtoRequest {
  private String title;

  private String author;
}
