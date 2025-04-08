### @RestControllerAdvice vs @ControllerAdvice

In Spring Boot, both @RestControllerAdvice and @ControllerAdvice are used for global exception handling,
but they serve different purposes.

#### @ControllerAdvice

It's a specialization of @Component and applies to regular MVC controllers annotated with @Controller.
Methods in a @ControllerAdvice class typically return views (like HTML pages).
If you use this in a REST API context, you must annotate your methods with @ResponseBody to return JSON or other
serialized responses.

#### @RestControllerAdvice

It’s a convenience annotation that combines @ControllerAdvice + @ResponseBody.
Meant for REST APIs using @RestController.
Automatically serializes return values (usually to JSON), so you don't need to add @ResponseBody to each method.

#### Use Case

* @ControllerAdvice - Building a web app returning HTML
* @RestControllerAdvice - Building a REST API returning JSON/XML	