package com.malexj.handler;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;

/*
 * Represents a message containing data from Raspberry Pi sensors
 */
public record RaspberrySensorMessage(
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
        LocalDateTime timestamp,
    String info,
    String sensors) {}
