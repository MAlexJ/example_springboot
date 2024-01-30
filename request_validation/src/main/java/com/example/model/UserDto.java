package com.example.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class UserDto {

  @Min(0)
  private int id;

  @Min(18)
  @Max(60)
  private int age;

  @NotNull(message = "name shouldn't null")
  private String name;

  @Email(message = "Email field should be in the format of an e-mail address")
  @NotNull(message = "email shouldn't null")
  private String email;
}
