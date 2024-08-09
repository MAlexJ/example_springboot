package com.malex.request_validation.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@ToString
public class User {

    @Min(0)
    @NotNull
    private Integer id;

    @Min(18)
    @Max(60)
    private Integer age;

    @NotNull(message = "name shouldn't null")
    private String name;

    @Email(message = "Email field should be in the format of an e-mail address")
    @NotNull(message = "email shouldn't null")
    private String email;
}
