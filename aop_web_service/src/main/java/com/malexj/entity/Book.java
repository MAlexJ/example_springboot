package com.malexj.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.ToString;

@Data
@Entity(name = "book")
@ToString
public class Book {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  public Long id;

  private String title;

  private String author;
}
