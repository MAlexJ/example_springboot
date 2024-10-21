package com.malex.uri_component_builder_in_spring.services;

import com.malex.uri_component_builder_in_spring.base.UriBuilderBase;
import java.net.URI;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j
@Service
public class UriWithEncodedOrNot extends UriBuilderBase {

  public void run() {
    // without
    uriToUriStringWithoutEncode();
    constructingUriWithoutEncode();

    // encode
    uriToUriStringEncode();
    constructUriEncoded();
  }

  private void uriToUriStringWithoutEncode() {
    var uriComponents =
        UriComponentsBuilder.fromUriString(URL_TEMPLATE)
            .queryParam("q", "{q}")
            .buildAndExpand("_._;_:_help_", "a b");

    String toUriString = uriComponents.toUriString();
    verifyURI(toUriString, "https://example.com/hotels/_._;_:_help_?q=a b");
  }

  private void constructingUriWithoutEncode() {
    UriComponents uriComponents =
        UriComponentsBuilder.newInstance()
            .scheme("http")
            .host("www.baeldung.com")
            .path("/java 23")
            //            .encode()
            .build();

    var toString = uriComponents.toString();
    verifyURI(toString, "http://www.baeldung.com/java 23");

    var toUriString = uriComponents.toUriString();
    verifyURI(toUriString, "http://www.baeldung.com/java 23");

    URI uri = uriComponents.toUri();
    // but: if we up-cast to string
    verifyURI(uri, "http://www.baeldung.com/java%2023");
  }

  private void uriToUriStringEncode() {
    // ' ' = %20
    var param = " ";
    var uriComponents =
        UriComponentsBuilder.fromUriString(URL_TEMPLATE) //
            .queryParam("q", param)
            .encode()
            .build();

    String string = uriComponents.toString();
    verifyURI(string, "https://example.com/hotels/{hotel}?q=%20");

    // '%' = %25
    param = "%";
    uriComponents =
        UriComponentsBuilder.fromUriString(URL_TEMPLATE).queryParam("new", param).encode().build();
    verifyURI(uriComponents.toUriString(), "https://example.com/hotels/{hotel}?new=%25");
  }

  public void constructUriEncoded() {
    UriComponents uriComponents =
        UriComponentsBuilder.newInstance()
            .scheme("http")
            .host("www.baeldung.com")
            .path("/spring 5")
            .encode()
            .build();

    String uriString = uriComponents.toUriString();
    verifyURI(uriString, "http://www.baeldung.com/spring%205");
  }
}
