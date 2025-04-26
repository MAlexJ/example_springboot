package com.malex.service.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class MessageEventRequest {
  private MessageType type;
  private String message;
  private String room;
}
