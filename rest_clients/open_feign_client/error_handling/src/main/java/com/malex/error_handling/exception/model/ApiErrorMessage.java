package com.malex.error_handling.exception.model;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import com.fasterxml.jackson.annotation.JsonInclude;

/*
 * ApiErrorMessage will be translated to a JSON body in the response, such that our JSON API
 * answers with JSON also in case of error and not with the Spring default, i.e. an HTML page:
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Getter
@Setter
@AllArgsConstructor
public class ApiErrorMessage {
    private final UUID id = UUID.randomUUID();
    private final int status;
    private final String error;
    private final String message;
    private final LocalDateTime timestamp = LocalDateTime.now(Clock.systemUTC());
    private final String path;
}
