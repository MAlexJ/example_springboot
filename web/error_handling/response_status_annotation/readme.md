### @ResponseStatus annotation

@ResponseStatus is a Spring annotation used to automatically return a specific HTTP status code when
a certain exception is thrown.

It allows you to define what HTTP status should be sent without manually building a ResponseEntity.

```
@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super(message);
       }
}
```

Now use it in a controller:

```
@GetMapping("/user/{id}")
public User getUser(@PathVariable Long id) {
    return userRepository.findById(id)
        .orElseThrow(() -> new UserNotFoundException("User not found"));
}
```

When this exception is thrown, the response will have:

* HTTP Status: 404 Not Found
* Response body: (optional) default error structure