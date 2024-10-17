### Error Handling for REST with Spring

Before Spring 3.2, the two main approaches to handling exceptions in a Spring MVC application
were HandlerExceptionResolver or the @ExceptionHandler annotation.
Both have some clear downsides.

Since 3.2, we’ve had the @ControllerAdvice annotation to address the limitations of the previous two solutions
and to promote a unified exception handling throughout a whole application.

Now Spring 5 introduces the ResponseStatusException class — a fast way for basic error handling in our REST APIs.

All of these do have one thing in common:
They deal with the separation of concerns very well.
The app can throw exceptions normally to indicate a failure of some kind, which will then be handled separately.

1. Solution 1: the Controller-Level @ExceptionHandler

2. Solution 2: the HandlerExceptionResolver

3. Solution 3: @ControllerAdvice

4. Solution 4: ResponseStatusException (Spring 5 and Above)
   Spring 5 introduced the ResponseStatusException class.
   We can create an instance of it providing an HttpStatus and optionally a reason and a cause:
   For more details and further examples, see - https://www.baeldung.com/spring-response-status-exception

link: https://www.baeldung.com/exception-handling-for-rest-with-spring#exceptionhandler