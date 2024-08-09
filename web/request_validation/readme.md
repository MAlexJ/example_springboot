### Spring JSON request validation

include for logging properties:

application.yaml

```
server:
  error:
    #
    # 1. with an always value;
    # includes the stacktrace in both the HTML and the JSON default response
    #
    include-stacktrace: always
    #
    # 2. Spring Boot hides the message field in the response to avoid leaking sensitive information;
    # we can use this property with an always value to enable it
    #
    include-message: always
```

link: https://www.baeldung.com/spring-boot-bean-validation

When it comes to validating user input, Spring Boot provides strong support for this common,
yet critical, task straight out of the box.

gradle dependencies

```
    implementation 'org.springframework.boot:spring-boot-starter-validation'
```

#### Validate Request Body and Parameter in Spring Boot

link: https://medium.com/@tericcabrel/validate-request-body-and-parameter-in-spring-boot-53ca77f97fe9

