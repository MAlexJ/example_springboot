package com.malexj.model.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
public class BooksDtoResponse {

  private List<BookDtoResponse> books;
}
