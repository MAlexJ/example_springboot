package com.malexj.scheduler;

import com.malexj.handler.RaspberrySensorMessage;
import com.malexj.handler.SensorWebSocketHandler;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class SchedulerProcessor {

  private final SensorWebSocketHandler sensorWebSocketHandler;

  @Async
  @Scheduled(cron = "0/1 * * * * *")
  public void processLastMessage() {
    log.info("Sending sensor data");
    sensorWebSocketHandler.sendSensorData(
        new RaspberrySensorMessage(LocalDateTime.now(), "info", "sensor data"));
  }
}
