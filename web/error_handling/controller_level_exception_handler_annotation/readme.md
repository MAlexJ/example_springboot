###  Local Exception Handling (Controller-Level)

link: https://www.baeldung.com/exception-handling-for-rest-with-spring#1-local-exception-handling-controller-level

We can place such handler methods in the controller class:

```
@RestController
public class FooController {
//...

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(CustomException.class)
    public void handleException() {
        // ...
    }
}
```

We could use this approach whenever we need controller-specific exception handling. 
But it has the drawback that we cannot use it in multiple controllers unless we put it in a base class 
and use inheritance. 

But there’s another approach that fits better in the sense of composition over inheritance.
