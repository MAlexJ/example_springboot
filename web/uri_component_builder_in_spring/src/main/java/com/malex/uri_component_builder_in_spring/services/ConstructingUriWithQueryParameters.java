package com.malex.uri_component_builder_in_spring.services;

import com.malex.uri_component_builder_in_spring.base.UriBuilderBase;
import java.net.URI;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j
@Service
public class ConstructingUriWithQueryParameters extends UriBuilderBase {

  public void runSamples() {
    immutableUriComponents();
    uriComponentsBuilder();
    expandFromUriString();
    buildAndExpandFromUriString();
    buildOneLineTemplate();
    toUriToUriStringToString();
  }

  /*
   *  UriComponents class – an immutable container for URI components.
   */
  private void immutableUriComponents() {

    UriComponents uriComponents = UriComponentsBuilder.fromUriString(URL_TEMPLATE).build();

    String rawUrl = URL_TEMPLATE.replace("{", "%7B").replace("}", "%7D");
    verifyURI(uriComponents.toUri(), rawUrl);

    UriComponents newComponents = uriComponents.encode();
    verifyURI(newComponents.toUri(), rawUrl);

    verifyURI(uriComponents.toUri(), rawUrl);
  }

  private void uriComponentsBuilder() {
    UriComponentsBuilder.fromUriString(URL_TEMPLATE);

    UriComponentsBuilder.fromUri(URI.create(URL_TEMPLATE.replace("/{hotel}", "")));

    UriComponentsBuilder.fromHttpUrl(URL_TEMPLATE);

    UriComponentsBuilder.fromPath(URL_TEMPLATE);

    UriComponentsBuilder.fromOriginHeader(URL_TEMPLATE);

    UriComponentsBuilder.newInstance();
  }

  private void expandFromUriString() {
    UriComponents uriComponents =
        UriComponentsBuilder.fromUriString(URL_TEMPLATE)
            /*
             * UriComponentsBuilder queryParam(....) method
             *
             * Append the given query parameter.
             * Both the parameter name and values may contain URI template variables to be expanded later from values.
             * If no values are given, the resulting URI will contain the query parameter name only:
             * "?foo" instead of "?foo=bar".
             */
            .queryParam("q", "{q}")

            /*
             * encode() method
             * Request to have the URI template pre-encoded at build time,
             * and URI variables encoded separately when expanded.
             *
             * For example ';' is legal in a path but has reserved meaning.
             * This method replaces ";" with "%3B" in URI variables but not in the URI template.
             */
            .encode()
            /*
             * Build a UriComponents instance from the various components contained in this builder.
             */
            .build();

    /*
     * UriComponents expand(...) method
     *
     * Replace all URI template variables with the values from a given array.
     * The given array represents variable values. The order of variables is significant.
     */
    URI uri =
        uriComponents
            .expand("Weston", "123")
            /*
             *  Create a URI from this instance as follows:
             *
             * If the current instance is encoded, form the full URI String via toUriString(),
             * and then pass it to the single argument URI constructor which preserves percent encoding.
             *
             * If not yet encoded, pass individual URI component values to the multi-argument URI constructor
             * which quotes illegal characters that cannot appear in their respective URI component.
             */
            .toUri();

    // verify
    verifyURI(uri, "https://example.com/hotels/Weston?q=123");
  }

  private void buildAndExpandFromUriString() {

    // UriComponentsBuilder
    UriComponentsBuilder firstUriComponentsBuilder =
        UriComponentsBuilder.fromUriString(URL_TEMPLATE);

    // UriComponentsBuilder
    UriComponentsBuilder secondUriComponentsBuilder =
        firstUriComponentsBuilder.queryParam("q", "{q}");

    // UriComponents
    UriComponents uriComponents = secondUriComponentsBuilder.buildAndExpand("Hosting", "2345");

    // URI
    URI uri = uriComponents.toUri();

    verifyURI(uri, "https://example.com/hotels/Hosting?q=2345");
  }

  private void buildOneLineTemplate() {
    URI uri =
        UriComponentsBuilder.fromUriString("https://example.com/hotels/{hotel}?filter={q}")
            .build("_one_", "_two_");
    verifyURI(uri, "https://example.com/hotels/_one_?filter=_two_");
  }

  private void toUriToUriStringToString() {

    // UriComponentsBuilder
    var uriComponents =
        UriComponentsBuilder.fromUriString(URL_TEMPLATE)
            .queryParam("q", "{q}")
            .buildAndExpand("Westin", "123");

    /*
     * Create a URI from this instance as follows:
     *
     * If the current instance is encoded, form the full URI String via toUriString(),
     * and then pass it to the single argument URI constructor which preserves percent encoding.
     *
     * If not yet encoded, pass individual URI component values to the multi-argument URI constructor
     * which quotes illegal characters that cannot appear in their respective URI component.
     */
    URI toUri = uriComponents.toUri();
    verifyURI(toUri, "https://example.com/hotels/Westin?q=123");

    /*
     * Concatenate all URI components to return the fully formed URI String.
     *
     * This method amounts to simple String concatenation of the current URI component values
     * and as such the result may contain illegal URI characters,
     * for example if URI variables have not been expanded
     * or if encoding has not been applied via UriComponentsBuilder. encode() or encode().
     */
    String toUriString = uriComponents.toUriString();
    verifyURI(toUriString, "https://example.com/hotels/Westin?q=123");

    String toString = uriComponents.toString();
    verifyURI(toString, "https://example.com/hotels/Westin?q=123");
  }
}
