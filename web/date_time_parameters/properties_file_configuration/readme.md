### Configure Date-Time in Properties File

link: https://www.baeldung.com/spring-date-parameters#applicationProperties

Spring also gives us the option to set global date-time formats via the application properties file.
There are three individual parameters for the date, date-time, and time format:

```
spring.mvc.format.date=yyyy-MM-dd
spring.mvc.format.date-time=yyyy-MM-dd HH:mm:ss
spring.mvc.format.time=HH:mm:ss
```

All of these parameters can be replaced with an iso value. 
For example, setting the date-time parameter as:

```
spring.mvc.format.date-time=iso
```

will be equal to ISO-8601 formatting:

```
spring.mvc.format.date-time=yyyy-MM-dd HH:mm:ss
```
