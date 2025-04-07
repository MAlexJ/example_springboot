### RFC 9457 Problem Details for HTTP APIs

link: https://www.rfc-editor.org/rfc/rfc9457.html

This document defines a "problem detail" to carry machine-readable details of errors in HTTP response content
to avoid the need to define new error response formats for HTTP APIs.

#### Why should we use RFC-9457 ?

Using RFC-9457 to model error responses in a RESFTful API has several benefits:

* Standardization:
  It provides a standard way to represent and deal with error responses accross different APIs,
  which makes error handling easier for clients.

* Documentation:
  The type field can be used to provide links to the documentation that describes the errors
  in more details.

* Usability:
  The ProblemDetails object can be parsed programmatically by clients to extract the error details
  and do something with it.

* Extensibility:
  The ProblemDetails object is extensible, thus you can add custom informations to provide
  more context about the error.

#### Returning Errors Using ProblemDetail in Spring Boot

link: https://www.baeldung.com/spring-boot-return-errors-problemdetail

#### ProblemDetail Specification

The ProblemDetail specification is part of the RFC 7807 standard.
It defines a consistent structure for error responses, including fields like type, title, status, detail,
and instance. This standardization helps API developers and consumers by providing a common format
for error information.

Implementing ProblemDetail ensures that our error responses are predictable and easy to understand.
That in turn improves overall communication between our API and its clients.

* Enabling ProblemDetail Using Application Property

```
spring.mvc.problemdetails.enabled=true
```

* Implementing ProblemDetail in Exception Handler

```
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(InvalidInputException.class)
    public ProblemDetail handleInvalidInputException(InvalidInputException e, WebRequest request) {
        ProblemDetail problemDetail
            = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getMessage());
        problemDetail.setInstance(URI.create("discount"));
        return problemDetail;
    }
}
```