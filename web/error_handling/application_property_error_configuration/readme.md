### Spring Boot Support

yaml file configuration:

```
spring:
  application:
    name: handler_exception_resolver_implementation

  threads:
    virtual:
      enabled: true

server:
  error:
    include-message: always
```

##### Spring Boot provides an ErrorController implementation to handle errors in a sensible way.

In a nutshell, it serves a fallback error page for browsers (a.k.a. the Whitelabel Error Page)
and a JSON response for RESTful, non-HTML requests:

```
{
"timestamp": "2019-01-17T16:12:45.977+0000",
"status": 500,
"error": "Internal Server Error",
"message": "Error processing the request!",
"path": "/my-endpoint-with-exceptions"
}

```

As usual, Spring Boot allows configuring these features with properties:

* **server.error.whitelabel.enabled**
  can be used to disable the Whitelabel Error Page and rely on the servlet container to provide an HTML error message

* **server.error.include-stacktrace**
  with an always value; includes the stacktrace in both the HTML and the JSON default response

* **server.error.include-message**
  since version 2.3, Spring Boot hides the message field in the response to avoid leaking sensitive information
  we can use this property with an always value to enable it

Apart from these properties, we can provide our own view-resolver mapping for /error, overriding the Whitelabel Page.

We can also customize attributes that we want to show in the response by including an ErrorAttributes bean in context.
We can extend the DefaultErrorAttributes class provided by Spring Boot to make things easier:

```
@Component
public class MyCustomErrorAttributes extends DefaultErrorAttributes {

    @Override
    public Map<String, Object> getErrorAttributes(
      WebRequest webRequest, ErrorAttributeOptions options) {
        Map<String, Object> errorAttributes = 
          super.getErrorAttributes(webRequest, options);
        errorAttributes.put("locale", webRequest.getLocale()
            .toString());
        errorAttributes.remove("error");

        //...

        return errorAttributes;
    }
}
```

If we want to go further and define (or override) how the application will handle errors for a particular content type,
we can register an ErrorController bean.

Again, we can make use of the default BasicErrorController provided by Spring Boot to help us out.

For example, imagine we want to customize how our application handles errors triggered in XML endpoints.
All we have to do is define a public method using the @RequestMapping,
and stating it produces application/xml media type:

```
@Component
public class MyErrorController extends BasicErrorController {

    public MyErrorController(
      ErrorAttributes errorAttributes, ServerProperties serverProperties) {
        super(errorAttributes, serverProperties.getError());
    }

    @RequestMapping(produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<Map<String, Object>> xmlError(HttpServletRequest request) {
        
    // ...

    }
}
```

Note: here we’re still relying on the server.error.*
Boot properties we might have been defined in our project, which are bound to the ServerProperties bean.
