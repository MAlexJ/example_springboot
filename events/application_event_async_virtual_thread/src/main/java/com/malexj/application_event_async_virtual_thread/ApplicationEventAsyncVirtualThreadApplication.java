package com.malexj.application_event_async_virtual_thread;

import com.malexj.application_event_async_virtual_thread.publisher.Publisher;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@RequiredArgsConstructor
@SpringBootApplication
public class ApplicationEventAsyncVirtualThreadApplication implements CommandLineRunner {

  private final Publisher publisher;

  public static void main(String[] args) {
    SpringApplication.run(ApplicationEventAsyncVirtualThreadApplication.class, args);
  }

  @Override
  public void run(String... args) {
    publisher.publishEvent("Hello!");
  }
}
