package com.malex.uri_component_builder_in_spring.base;

import java.net.URI;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UriBuilderBase {

  protected static final String URL_TEMPLATE = "https://example.com/hotels/{hotel}";


  protected void verifyURI(URI actualUri, String expected) {
    var validUri =
        Optional.of(actualUri)
            .map(URI::toASCIIString)
            .filter(url -> url.equals(expected))
            .orElseThrow(
                () -> new IllegalArgumentException("Invalid URI , actual - " + actualUri));
    print(validUri);
  }

  protected void verifyURI(String actualUri, String expected) {
    var validUri =
        Optional.of(actualUri)
            .filter(url -> url.equals(expected))
            .orElseThrow(
                () -> new IllegalArgumentException("Invalid URI , actual - " + actualUri));
    print(validUri);
  }

  private void print(String validUri) {
    log.info("URL: {}", validUri);
  }
}
