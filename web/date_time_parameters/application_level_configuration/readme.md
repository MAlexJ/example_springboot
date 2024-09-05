### Configuring a Global Date and Time Format

link: https://docs.spring.io/spring-framework/reference/core/validation/format-configuring-formatting-globaldatetimeformat.html
second: https://www.baeldung.com/spring-date-parameters

By default, date and time fields not annotated with @DateTimeFormat are converted from strings by using the
DateFormat.SHORT style.

If you prefer, you can change this by defining your own global format.
To do that, ensure that Spring does not register default formatters.

Instead, register formatters manually with the help of:

* org.springframework.format.datetime.standard.DateTimeFormatterRegistrar
* org.springframework.format.datetime.DateFormatterRegistrar


###### Convert Date Parameters at the Application Level

Another way to handle date and time object conversion in Spring is to provide a global configuration. 
By following the official documentation, we should extend the WebMvcConfigurationSupport configuration 
and its mvcConversionService method:

```
@Configuration
public class DateTimeConfig extends WebMvcConfigurationSupport {

    @Bean
    @Override
    public FormattingConversionService mvcConversionService() {
        DefaultFormattingConversionService conversionService = new DefaultFormattingConversionService(false);

        DateTimeFormatterRegistrar dateTimeRegistrar = new DateTimeFormatterRegistrar();
        dateTimeRegistrar.setDateFormatter(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        dateTimeRegistrar.setDateTimeFormatter(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss"));
        dateTimeRegistrar.registerFormatters(conversionService);

        DateFormatterRegistrar dateRegistrar = new DateFormatterRegistrar();
        dateRegistrar.setFormatter(new DateFormatter("dd.MM.yyyy"));
        dateRegistrar.registerFormatters(conversionService);

        return conversionService;
    }
}
```

First, we create DefaultFormattingConversionService with a false parameter, 
which means Spring won’t register any formatters by default.

Then we need to register our custom formats for date and date-time parameters.
We do this by registering two custom formatting registrars. 
The first one, DateTimeFormatterRegistar, will be responsible for parsing the LocalDate and LocaDateTime objects. 
The second one, DateFormattingRegistrar, will handle the Date object.