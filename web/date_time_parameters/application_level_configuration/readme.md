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
  
For example, the following Java configuration registers a global yyyyMMdd format:

```
@Configuration
public class AppConfig {

	@Bean
	public FormattingConversionService conversionService() {

		// Use the DefaultFormattingConversionService but do not register defaults
		DefaultFormattingConversionService conversionService =
			new DefaultFormattingConversionService(false);

		// Ensure @NumberFormat is still supported
		conversionService.addFormatterForFieldAnnotation(
			new NumberFormatAnnotationFormatterFactory());

		// Register JSR-310 date conversion with a specific global format
		DateTimeFormatterRegistrar dateTimeRegistrar = new DateTimeFormatterRegistrar();
		dateTimeRegistrar.setDateFormatter(DateTimeFormatter.ofPattern("yyyyMMdd"));
		dateTimeRegistrar.registerFormatters(conversionService);

		// Register date conversion with a specific global format
		DateFormatterRegistrar dateRegistrar = new DateFormatterRegistrar();
		dateRegistrar.setFormatter(new DateFormatter("yyyyMMdd"));
		dateRegistrar.registerFormatters(conversionService);

		return conversionService;
	}
}
```