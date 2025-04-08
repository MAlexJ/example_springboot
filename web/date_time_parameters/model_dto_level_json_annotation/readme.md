### Formatting JSON Dates in Spring Boot

Using @JsonFormat on a Date Field

We can use the @JsonFormat annotation to format a specific field:

```
public class Contact {

    // other fields

    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate birthday;
     
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastUpdate;

    // standard getters and setters

}
```

On the birthday field, we use a pattern that renders only the date,
while on the lastUpdate field, we also include the time.

We used the Java 8 date types, which are quite handy for dealing with temporal types.

Of course, if we need to use the legacy types such as java.util.Date, we can use the annotation in the same way:

```
public class ContactWithJavaUtilDate {

     // other fields

     @JsonFormat(pattern="yyyy-MM-dd")
     private Date birthday;
     
     @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
     private Date lastUpdate;

     // standard getters and setters
}
```

Finally, let’s take a look at the output rendered by using the @JsonFormat with the given date format:

```
{
"birthday": "2019-02-03",
"lastUpdate": "2019-02-03 10:08:02"
}
```

As we can see, using the @JsonFormat annotation is an excellent way to format a particular date field.

However, we should only use it when we need specific formatting for fields.
If we want to have a general format for all dates in our application, there are better ways to achieve this as we’ll see
later.

link: https://www.baeldung.com/spring-boot-formatting-json-dates

