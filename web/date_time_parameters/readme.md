### Working with Date Parameters in Spring

link: https://www.baeldung.com/spring-date-parameters

The Problem

Let’s consider a controller with three methods that accepts Date, LocalDate and LocalDateTime parameters:

```
@RestController
public class DateTimeController {

    @PostMapping("/date")
    public void date(@RequestParam("date") Date date) {
        // ...
    }

    @PostMapping("/localdate")
    public void localDate(@RequestParam("localDate") LocalDate localDate) {
        // ...
    }

    @PostMapping("/localdatetime")
    public void dateTime(@RequestParam("localDateTime") LocalDateTime localDateTime) {
        // ...
    }
}
```

When sending a POST request to any of those methods with a parameter formatted in accordance with ISO 8601, we’ll get an
exception.

For example, when sending “2018-10-22” to the /date endpoint, we’ll get a bad request error with a message similar to
this:

```
Failed to convert value of type 'java.lang.String' to required type 'java.time.LocalDate';
nested exception is org.springframework.core.convert.ConversionFailedException.
```

This is because by default, Spring cannot convert String parameters to any date or time object.

Solution:

#### 1.Convert Date Parameters on Request Level

#### 2. Convert Date Parameters at the Application Level

#### 3. Configure Date-Time in Properties File