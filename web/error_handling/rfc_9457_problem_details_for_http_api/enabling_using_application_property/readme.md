### RFC 7807 Problem Details

The first one is to set the property `spring.mvc.problemdetails.enabled`  to `true` in the `application.properties`

application.yaml file

```yaml
spring:
  mvc:
    problemdetails:
      enabled: true
```

This property enables the automatic use of ProblemDetail for error handling in MVC (servlet stack) based applications.

For reactive applications, we’d add the following property:

```
spring.webflux.problemdetails.enabled=true
```

Once enabled, Spring reports errors using ProblemDetail:

```
{
    "type": "about:blank",
    "title": "Bad Request",
    "status": 400,
    "detail": "Invalid request content.",
    "instance": "/sales/calculate"
}
```

This property provides ProblemDetail automatically in error handling.
Also, we can turn it off if it’s not needed.

### Note: Problem detail impl

1. Custom Exceptions (like your InvalidInputException) are NOT handled by default
2. Built-in Exceptions Spring Handles as ProblemDetail (not all)
3. It support spring-validation (spring-boot-starter-validation) implementation

#### 1. Built-in Exceptions Spring Handles as ProblemDetail

When enabled, Spring will automatically turn these exceptions into RFC 9457-compliant responses.

**ResponseStatusException** - As defined, You define the status/code manually

```
        Exception                      HTTP Status	           Description
-----------------------------------------------------------------------------------------------        
HttpRequestMethodNotSupportedException	  405	Method not allowed (e.g., POST instead of GET)
HttpMediaTypeNotSupportedException	      415	Unsupported Content-Type
HttpMediaTypeNotAcceptableException	      406	Cannot produce acceptable Accept type
MissingPathVariableException	          500	Required path variable missing
MissingServletRequestParameterException	  400	Required query parameter missing
ServletRequestBindingException	          400	Binding issue with query/header/path param
ConversionNotSupportedException	          500	Failed to convert controller argument
TypeMismatchException	                  400	Type mismatch (e.g., expected number, got string)
HttpMessageNotReadableException	          400	Request body unreadable (e.g., malformed JSON)
HttpMessageNotWritableException	          500	Error serializing response
MethodArgumentNotValidException	          400	Bean validation (@Valid) failed
BindException	                          400	Binding errors (e.g., form or DTO binding)
NoHandlerFoundException	                  404	No matching controller
ResponseStatusException	            As defined	You define the status/code manually
```

#### 2. Spring Validation Failure

Add dependency:

```
dependencies {
    implementation 'org.springframework.boot:spring-boot-starter-validation'
}
```

Add validation condition:

```
@RestController
public class TestRestController_with_Spring_Validation {

  @PostMapping("/spring/validation")
  public ResponseEntity<String> calculate(@RequestBody @Valid OperationRequest operationRequest) {
    return ResponseEntity.ok(operationRequest.toString());
  }

  public record OperationRequest(
      @Min(value = 30, message = "base price greater than 30$ not allowed.") Double basePrice,
      Double discount) {}
}
```

#### 3. Custom Exceptions (like your InvalidInputException) are NOT handled by default

If you throw a custom exception:

```throw new InvalidInputException("Discount cannot be null");```

Spring doesn’t know how to turn this into a ProblemDetail unless you write a handler for it

