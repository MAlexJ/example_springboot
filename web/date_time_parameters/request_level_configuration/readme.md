### Convert Date Parameters on Request Level

One of the ways to handle this problem is to annotate the parameters with the @DateTimeFormat annotation,
and provide a formatting pattern parameter:

```
RestController
public class DateTimeController {

    @PostMapping("/date")
    public void date(@RequestParam("date") 
      @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date date) {
        // ...
    }

    @PostMapping("/local-date")
    public void localDate(@RequestParam("localDate") 
      @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate localDate) {
        // ...
    }

    @PostMapping("/local-date-time")
    public void dateTime(@RequestParam("localDateTime") 
      @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime localDateTime) {
        // ...
    }
}
```


This way the Strings will be properly converted to date objects, provided the Strings are formatted using the ISO 8601 format.

We can also use our own conversion patterns by providing a pattern parameter in the @DateTimeFormat annotation:

```
@PostMapping("/date")
public void date(@RequestParam("date") 
  @DateTimeFormat(pattern = "dd.MM.yyyy") Date date) {
    // ...
}
```