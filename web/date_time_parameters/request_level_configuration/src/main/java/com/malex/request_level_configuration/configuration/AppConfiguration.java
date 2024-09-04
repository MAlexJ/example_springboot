package com.malex.request_level_configuration.configuration;

import com.malex.request_level_configuration.dto.Event;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfiguration {

  @Bean
  public List<Event> events() {
    var list =
        List.of( //
            Event.builder()
                .id(1)
                .text("First event:")
                .start(LocalDateTime.of(LocalDate.of(2024, 9, 4), LocalTime.of(9, 30, 0)))
                .end(LocalDateTime.of(LocalDate.of(2024, 9, 4), LocalTime.of(10, 30, 0)))
                .backColor("#b6d7a8")
                .borderColor("darker")
                .build(), //
            Event.builder()
                .id(2)
                .text("Samples")
                .start(LocalDateTime.of(LocalDate.of(2024, 9, 4), LocalTime.of(11, 0, 0)))
                .end(LocalDateTime.of(LocalDate.of(2024, 9, 4), LocalTime.of(13, 0, 0)))
                .backColor("#b6d7a8")
                .borderColor("darker")
                .build(),
            Event.builder()
                .id(3)
                .text("Ad new workflow")
                .start(LocalDateTime.of(LocalDate.of(2024, 9, 4), LocalTime.of(15, 30, 0)))
                .end(LocalDateTime.of(LocalDate.of(2024, 9, 4), LocalTime.of(16, 30, 0)))
                .backColor("#b6d7a8")
                .borderColor("darker")
                .build());
    return new ArrayList<>(list);
  }
}
