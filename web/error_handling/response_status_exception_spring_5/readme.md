### ResponseStatusException

What is ResponseStatusException in Spring?

ResponseStatusException is a runtime exception introduced in Spring 5 that allows you to programmatically
return a specific HTTP status code along with an optional reason/message and cause.

It’s commonly used in REST APIs to indicate errors without needing to define custom exception classes or global
handlers.

example:

```
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@GetMapping("/user/{id}")
public User getUser(@PathVariable Long id) {
    return userRepository.findById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
}
```

#### ResponseStatusException

ResponseStatusException is a programmatic alternative to @ResponseStatus and is the base class for exceptions
used for applying a status code to an HTTP response.

It’s a RuntimeException and hence not required to be explicitly added in a method signature.

link: https://www.baeldung.com/spring-response-status-exception#responsestatusexception

Spring provides 3 constructors to generate ResponseStatusException:

```
ResponseStatusException(HttpStatus status)

ResponseStatusException(HttpStatus status, java.lang.String reason)

ResponseStatusException(
  HttpStatus status, 
  java.lang.String reason, 
  java.lang.Throwable cause
)
```

ResponseStatusException, constructor arguments:

* status – an HTTP status set to the HTTP response
* reason – a message explaining the exception set to the HTTP response
* cause – a Throwable cause of the ResponseStatusException

Note: in Spring, HandlerExceptionResolver intercepts and processes any exception raised and not handled by a
Controller.

One of these handlers, ResponseStatusExceptionResolver, looks for any ResponseStatusException
or uncaught exceptions annotated by @ResponseStatus
and then extracts the HTTP Status code & reason and includes them in the HTTP response.