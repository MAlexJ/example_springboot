package com.malex.request_level_configuration.dto;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;

/*
 {
       id: 8,
       text: "Event 8",
       start: DayPilot.Date.today().addDays(5).addHours(13),
       end: DayPilot.Date.today().addDays(5).addHours(15),
       backColor: "#b6d7a8", // Light Green background
       borderColor: "darker"
     }
*/
@Data
@Builder
public class Event {
  public int id;
  public String text;
  private LocalDateTime start;
  private LocalDateTime end;
  private String backColor;
  private String borderColor;
}
