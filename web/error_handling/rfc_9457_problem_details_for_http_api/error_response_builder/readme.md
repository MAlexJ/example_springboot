### What is ErrorResponse.builder()?

It is a builder for the ErrorResponse interface introduced in Spring Framework 6,
designed to simplify the creation of Problem Detail error responses.

This is not a class you define yourself — it comes from the Spring Framework
and helps you programmatically build a ProblemDetail-compliant error response.

#### Where it comes from

Package: org.springframework.http.ProblemDetail
Interface: org.springframework.web.ErrorResponse
Builder: ErrorResponse.builder(...)

#### Typical Use Case

You use it in @ExceptionHandler methods when customizing how exceptions are converted into RFC 9457 responses:

```
import org.springframework.http.ProblemDetail;
import org.springframework.web.ErrorResponse;

@ExceptionHandler(MyBusinessException.class)
public ErrorResponse handleBusinessError(MyBusinessException ex, HttpServletRequest request) {
    return ErrorResponse.builder(ex, HttpStatus.BAD_REQUEST, ex.getMessage())
                .type(URI.create("https://example.com/errors/business"))
                .title("Business rule violation")
                .property("customCode", "BUSINESS_001")
                .build();
}
```

#### ProblemDetail vs ErrorResponse

1. ProblemDetail – The Payload

ProblemDetail is the actual error response data model (introduced in Spring Framework 6 / Spring Boot 3).

2. ErrorResponse – The Wrapper/Contract + Builder

ErrorResponse is an interface in Spring’s error handling system (package: org.springframework.web).

It acts as a wrapper around ProblemDetail, helping integrate it with Spring's internal exception handling mechanisms
(like @ExceptionHandler).

You can use ErrorResponse.builder(...) to build an error response that Spring will automatically convert
to ProblemDetail under the hood: