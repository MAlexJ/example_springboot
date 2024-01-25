package com.malexj.model.response;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class BookDtoResponse {
  public Long id;

  private String title;

  private String author;
}
