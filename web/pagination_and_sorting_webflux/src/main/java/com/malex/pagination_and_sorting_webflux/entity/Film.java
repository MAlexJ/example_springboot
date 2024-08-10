package com.malex.pagination_and_sorting_webflux.entity;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table(value = "films")
@AllArgsConstructor
public class Film {

  @Id public Long id;

  @Column("title")
  @NotNull
  @Size(
      min = 1,
      max = 255,
      message = "The property 'name' must be less than or equal to 255 characters.")
  public String title;
}
