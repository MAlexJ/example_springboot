### Using a custom exception class

link: https://medium.com/@RoussiAbdelghani/error-handling-in-spring-web-using-rfc-9457-specification-f2cc8398e285

#### 1. Enable `spring.mvc.problemdetails.enabled` property

The first one is to set the property `spring.mvc.problemdetails.enabled`  to `true` in the `application.properties`

application.yaml file

```yaml
spring:
  mvc:
    problemdetails:
      enabled: true
```

#### 2. Create custom Exception extends ErrorResponseException

If we want to return an RFC-9457 compliant Problem details when a user is not found
when calling the GET /users/{id}, we can create a custom exception class
that extends ErrorResponseException call the super constructor to produce a custom ProblemDetail object.

and finally here is the custom exception class:

```
public class UserNotFoundException extends ErrorResponseException {

    public UserNotFoundException(Long userId, String path) {
        super(HttpStatus.NOT_FOUND, problemDetailFrom("User with id " + userId + " not found", path), null);
    }

    private static ProblemDetail problemDetailFrom(String message, String path) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, message);
        problemDetail.setType(URI.create("http://localhost:8080/errors/not-found"));
        problemDetail.setTitle("User not found");
        problemDetail.setInstance(URI.create(path));
        problemDetail.setProperty("timestamp", Instant.now()); // additional data
        return problemDetail;
    }
}
```

but if we call the same endpoint with an invalid user id, we will get the following response:

```
HTTP/1.1 404 
Content-Type: application/problem+json
Transfer-Encoding: chunked
Date: Tue, 18 Mar 2024 20:07:45 GMT
Connection: close

{
  "type": "http://localhost:8080/errors/not-found",
  "title": "User not found",
  "status": 404,
  "detail": "User with id 2 not found",
  "instance": "/api/users",
  "timestamp": "2024-03-19T12:04:45.479867Z"
}
```
