package com.malex.logginglevel;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MyServiceImpl {

  public void print() {
    log.trace(">>> trace");
    log.debug(">>> debug");
    log.info(">>> info");
    log.warn(">>> warn");
    log.error(">>> error");
  }
}
