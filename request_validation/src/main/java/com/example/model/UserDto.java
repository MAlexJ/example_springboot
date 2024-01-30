package com.example.model;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class UserDto {

  private int id;

  private String name;

  private String email;
}
