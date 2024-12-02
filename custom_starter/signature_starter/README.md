### Springboot 3 starter

Tutorial: How to create your own custom Spring Boot Starter </br>
Link: https://www.youtube.com/watch?v=9m1bC57oWrc&t=1063s

1. @Autoconfiguration
2. create: resources/META-INF/spring >>> {fileName: org.springframework.boot.autoconfigure
   file.AutoConfiguration.imports }
3. add to file {fileName: org.springframework.boot.autoconfigure file.AutoConfiguration.imports } full path to starter
   config class
4. for autowired beans from module, add import configuration to config class

```
@Configuration
@Import(SignatureAutoConfiguration.class)
public class RunConfiguration {}
```

### Test rest controller

Link: https://habr.com/ru/companies/otus/articles/746414/

### Aggregating @Configuration classes with @Import



