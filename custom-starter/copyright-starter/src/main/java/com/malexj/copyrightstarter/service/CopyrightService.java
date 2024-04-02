package com.malexj.copyrightstarter.service;

import com.malexj.copyrightstarter.configuration.StarterCopyrightProperties;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CopyrightService {

  private final StarterCopyrightProperties properties;

  public String generateCopyright() {
    return String.format("(c) %s %s", LocalDate.now().getYear(), properties.getAuthor());
  }
}
