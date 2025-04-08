### @ControllerAdvice

Typically, the @ExceptionHandler, @InitBinder, and @ModelAttribute methods apply within the @Controller class
(or class hierarchy) in which they are declared.

If you want such methods to apply more globally (across controllers), you can declare them in a class annotated
with @ControllerAdvice or @RestControllerAdvice.

@ControllerAdvice is annotated with @Component, which means that such classes can be registered as Spring beans
through component scanning .

@RestControllerAdvice is a composed annotation that is annotated with both @ControllerAdvice and @ResponseBody,
which essentially means @ExceptionHandler methods are rendered to the response body through message conversion
(versus view resolution or template rendering).

On startup, the infrastructure classes for @RequestMapping and @ExceptionHandler methods detect Spring beans
annotated with @ControllerAdvice and then apply their methods at runtime.

Global @ExceptionHandler methods (from a @ControllerAdvice) are applied after local ones (from the @Controller).
By contrast, global @ModelAttribute and @InitBinder methods are applied before local ones.
By default, @ControllerAdvice methods apply to every request (that is, all controllers),
but you can narrow that down to a subset of controllers by using attributes on the annotation,
as the following example shows:

```
// Target all Controllers annotated with @RestController
@ControllerAdvice(annotations = RestController.class)
public class ExampleAdvice1 {}

// Target all Controllers within specific packages
@ControllerAdvice("org.example.controllers")
public class ExampleAdvice2 {}

// Target all Controllers assignable to specific classes
@ControllerAdvice(assignableTypes = {ControllerInterface.class, AbstractController.class})
public class ExampleAdvice3 {}
```

link: https://docs.spring.io/spring-framework/reference/web/webflux/controller/ann-advice.html

@ControllerAdvice is a Spring annotation used to handle exceptions globally across all your controllers.

It’s part of Spring’s exception handling mechanism and works great for REST APIs to return consistent error responses.

Think of it as a global exception handler for your application.

```
mport org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.http.ResponseEntity;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<String> handleResponseStatusException(ResponseStatusException ex) {
        return ResponseEntity
                .status(ex.getStatusCode())
                .body(ex.getReason());
    }
}
```