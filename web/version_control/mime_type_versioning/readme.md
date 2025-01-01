### MIME type versioning

When you develop REST APIs you may want to add different versions of that API in future, say v1, v2 etc..., 
and different users of our API should be able to call the desired version. 

Now there can be different approaches to solve this API versioning problem like:

* URI Versioning,
* Parameter Versioning,
* Header Versioning or MIME versioning.

link: https://jsonapi.org

MIME Types:

JSON:API has been properly registered with the IANA.
Its media type designation is application/vnd.api+json.

#### Media Types

If we would like to define the structure of the resource that we are rendering or consuming,
we can use media type to define.

It also helps in versioning the API.

* `consumes` - matched only if the `Content-Type` request `header` matches
* `produces` - matched only if the `Accept` request `header` matches

link:

```
@RestController
public class PersonController {
    
    @GetMapping(path = "/person", produces = "application/vnd.company.api-v1+json")
    public Person getPersonV1() {
        return new Person("Mr. ABC", "abc@gmail.com");
    }

    @GetMapping(path = "/person", produces = "application/vnd.company.api-v2+json")
    public Person getPersonV2() {
        return new Person("Mr. XYZ", "xyz@gmail.com");
    }
}
```

Now I hit our API with different versions and get the appropriate response from the API. Please check the output below:

```
application/vnd.company.api-v1+json Requesting version 1
application/vnd.company.api-v2+json Requesting version 2
```

#### HTTP headers

HTTP headers let the client and the server pass additional information with an HTTP request or response.

An HTTP header consists of its case-insensitive name followed by a colon (:), then by its value.
Whitespace before the value is ignored.

Headers can be grouped according to their contexts:

* Request headers
  Contain more information about the resource to be fetched, or about the client requesting the resource.

* Response headers
  Hold additional information about the response, like its location or about the server providing it.

* Representation headers
  Contain information about the body of the resource, like its MIME type, or encoding/compression applied.

* Payload headers
  Contain representation-independent information about payload data, including content length
  and the encoding used for transport.

##### Content-Type

link: https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Content-Type

The HTTP Content-Type representation header is used to indicate the original media type of resource before
any content encoding is applied.

In `responses`, the `Content-Type header` informs the client about the media type of the returned data.

In `requests` such as `POST or PUT`, the client uses the `Content-Type header` to specify the type of content being sent
to the server.

If a server implementation or configuration is strict about content type handling,
a `415 client error response` may be returned.

##### Accept

link: https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Accept

The HTTP `Accept request header` indicates which content types, expressed as MIME types,
the client is able to understand.

The server uses content negotiation to select one of the proposals and informs the client of the choice with
the Content-Type response header.

Browsers set required values for this header based on the context of the request.

For example, a browser uses different values in a request when fetching a CSS stylesheet, image, video, or a script.