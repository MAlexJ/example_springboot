### @RestControllerAdvice

@RestControllerAdvice is a Spring Boot annotation used to handle exceptions globally
for REST ful APIs in a clean and consistent way.

#### Exception Handling in Spring -Best Practices

In the realm of “Exception Handling in Spring,” following best practices is paramount to ensure the resilience and
reliability of your applications. Here are some key recommendations to consider:

* Precise Exception Selection: Exception handling begins with the selection of the right exception type. Choose
  exceptions that accurately reflect the error scenario to provide meaningful responses.

* Custom Error Messages: Tailor error messages to convey relevant information to users and developers. Include error
  codes, descriptions, and guidance for resolution.

* Logging and Monitoring: Implement robust logging to capture error details. Additionally, set up monitoring to
  proactively identify and address recurring issues.

* Global Exception Handling: Consider using a global exception handler to centralize common exception handling logic.
  This enhances code maintainability and reduces redundancy.

* Testing and Validation: Rigorously test your exception handling logic to ensure it functions as intended. Validate
  data inputs to preemptively catch errors.

* Documentation: Document your exception handling strategies and the responses provided. This documentation serves as a
  valuable resource for your development team and API consumers.

* Regular Updates: Review and update your exception handling mechanisms as your application evolves. Ensure they remain
  aligned with your application’s evolving requirements.

* Security Considerations: When handling exceptions, be mindful of potential security vulnerabilities. Avoid exposing
  sensitive information in error messages.

link: https://medium.com/@miguelangelperezdiaz444/mastering-exception-handling-in-spring-restcontrolleradvice-3bd85d473b0d

#### It’s a specialized:

* Targets @RestController classes.
* Automatically applies @ResponseBody to all its methods.
* Returns JSON (or other formats) instead of HTML views.

In simple terms:
It helps you catch and handle exceptions across your REST controllers in one place, and always returns JSON.

#### Key Features

* Global Exception Handling (e.g., @ExceptionHandler)
* Global Data Binding (e.g., @InitBinder)
* Global Model Attributes (e.g., @ModelAttribute)

But most commonly used for exception handling in REST APIs.

```
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgument(IllegalArgumentException ex) {
        return ResponseEntity
                .badRequest()
                .body("Invalid input: " + ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGeneralError(Exception ex) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Something went wrong!");
    }
}
```

#### targeting options:

1. @RestControllerAdvice(annotations = RestController.class)

```
// Targets only controllers annotated with @RestController
@RestControllerAdvice(annotations = RestController.class)
public class ExampleAdvice1 {}
```

Scope: Applies only to controllers that are annotated with @RestController.
Useful when you want to apply global advice just to REST endpoints (not to regular @Controller MVC ones).

2. @RestControllerAdvice("org.example.controllers")

```
// Targets controllers in a specific package
@RestControllerAdvice("org.example.controllers")
public class ExampleAdvice2 {}
```

Scope: Applies only to controllers located in the specified package (and subpackages).
Perfect for modular applications where different parts of the system have their own controller groups.

3. @RestControllerAdvice(assignableTypes = {ControllerInterface.class, AbstractController.class})

```
// Targets controllers that are assignable to specific classes or interfaces
@RestControllerAdvice(assignableTypes = {ControllerInterface.class, AbstractController.class})
public class ExampleAdvice3 {}
```   

Scope: Applies to controllers that extend or implement the specified classes or interfaces.
Great for applying consistent exception handling to a specific base type or hierarchy of controllers.

