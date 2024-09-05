package com.malex.configuration;

import java.time.format.DateTimeFormatter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.format.datetime.DateFormatterRegistrar;
import org.springframework.format.datetime.standard.DateTimeFormatterRegistrar;
import org.springframework.format.number.NumberFormatAnnotationFormatterFactory;
import org.springframework.format.support.DefaultFormattingConversionService;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
public class DateTimeConfig extends WebMvcConfigurationSupport {

  @Bean
  @Override
  public FormattingConversionService mvcConversionService() {

    // Use the DefaultFormattingConversionService but do not register defaults
    DefaultFormattingConversionService conversionService =
        new DefaultFormattingConversionService(false);

    // Ensure @NumberFormat is still supported
    conversionService.addFormatterForFieldAnnotation(new NumberFormatAnnotationFormatterFactory());

    // Register JSR-310 date conversion with a specific global format
    DateTimeFormatterRegistrar dateTimeRegistrar = new DateTimeFormatterRegistrar();
    dateTimeRegistrar.setDateFormatter(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    dateTimeRegistrar.setDateTimeFormatter(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss"));
    dateTimeRegistrar.registerFormatters(conversionService);

    // Register date conversion with a specific global format
    DateFormatterRegistrar dateRegistrar = new DateFormatterRegistrar();
    dateRegistrar.setFormatter(new DateFormatter("yyyy-MM-dd"));
    dateRegistrar.registerFormatters(conversionService);

    return conversionService;
  }
}
