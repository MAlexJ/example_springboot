### DispatcherServlet (only for web mvc)

Simply put, in the Front Controller design pattern, a single controller is responsible for directing incoming
HttpRequests to all of an application’s other controllers and handlers.

Spring’s DispatcherServlet implements this pattern and is, therefore, responsible for correctly coordinating the
HttpRequests to their right handlers.

#### DispatcherServlet Request Processing

DispatcherServlet handles an incoming HttpRequest, delegates the request, and processes that request according to the
configured HandlerAdapter interfaces

DispatcherServlet.properties:

```
# Default implementation classes for DispatcherServlet's strategy interfaces.
# Used as fallback when no matching beans are found in the DispatcherServlet context.
# Not meant to be customized by application developers.

org.springframework.web.servlet.LocaleResolver=org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver

org.springframework.web.servlet.ThemeResolver=org.springframework.web.servlet.theme.FixedThemeResolver

org.springframework.web.servlet.HandlerMapping=org.springframework.web.servlet.handler.BeanNameUrlHandlerMapping,\
	org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping,\
	org.springframework.web.servlet.function.support.RouterFunctionMapping

org.springframework.web.servlet.HandlerAdapter=org.springframework.web.servlet.mvc.HttpRequestHandlerAdapter,\
	org.springframework.web.servlet.mvc.SimpleControllerHandlerAdapter,\
	org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter,\
	org.springframework.web.servlet.function.support.HandlerFunctionAdapter


org.springframework.web.servlet.HandlerExceptionResolver=org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver,\
	org.springframework.web.servlet.mvc.annotation.ResponseStatusExceptionResolver,\
	org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver

org.springframework.web.servlet.RequestToViewNameTranslator=org.springframework.web.servlet.view.DefaultRequestToViewNameTranslator

org.springframework.web.servlet.ViewResolver=org.springframework.web.servlet.view.InternalResourceViewResolver

org.springframework.web.servlet.FlashMapManager=org.springframework.web.servlet.support.SessionFlashMapManager
```

### spring-boot-starter-web

https://github.com/spring-projects/spring-boot/blob/main/spring-boot-project/spring-boot-starters/spring-boot-starter-web/build.gradle

### Info

An Intro to the Spring DispatcherServlet
https://www.baeldung.com/spring-dispatcherservlet

DispatcherServlet in Spring Boot
https://hemanthcse1.medium.com/dispatcherservlet-in-spring-boot-9e6f7fdc7a45

Spring Boot — Filter, Handler Interceptor, DispatcherServlet
https://medium.com/@meral.ismailcan/spring-boot-filter-handler-interceptor-dispatcherservlet-ca0aee6aa048

The DispatcherServlet: The Engine of Request Handling in Spring Boot
https://medium.com/@lakshyachampion/the-dispatcherservlet-the-engine-of-request-handling-in-spring-boot-3a85c2bdbe6b#id_token=eyJhbGciOiJSUzI1NiIsImtpZCI6ImM4OGQ4MDlmNGRiOTQzZGY1M2RhN2FjY2ZkNDc3NjRkMDViYTM5MWYiLCJ0eXAiOiJKV1QifQ.eyJpc3MiOiJodHRwczovL2FjY291bnRzLmdvb2dsZS5jb20iLCJhenAiOiIyMTYyOTYwMzU4MzQtazFrNnFlMDYwczJ0cDJhMmphbTRsamRjbXMwMHN0dGcuYXBwcy5nb29nbGV1c2VyY29udGVudC5jb20iLCJhdWQiOiIyMTYyOTYwMzU4MzQtazFrNnFlMDYwczJ0cDJhMmphbTRsamRjbXMwMHN0dGcuYXBwcy5nb29nbGV1c2VyY29udGVudC5jb20iLCJzdWIiOiIxMDU4OTEwMjYxOTEzODE3MzY2MTUiLCJlbWFpbCI6ImFsZXhtYXhpbW92akBnbWFpbC5jb20iLCJlbWFpbF92ZXJpZmllZCI6dHJ1ZSwibmJmIjoxNzMwNTc3Mjg2LCJuYW1lIjoiQWxleCBNIiwicGljdHVyZSI6Imh0dHBzOi8vbGgzLmdvb2dsZXVzZXJjb250ZW50LmNvbS9hL0FDZzhvY0wta3R2X1ZIendheEtSMG5VUkQwTDhtQllSM2RHMnhvQ29ONmxrUUJGUW16LVliUDF3PXM5Ni1jIiwiZ2l2ZW5fbmFtZSI6IkFsZXgiLCJmYW1pbHlfbmFtZSI6Ik0iLCJpYXQiOjE3MzA1Nzc1ODYsImV4cCI6MTczMDU4MTE4NiwianRpIjoiNGZjYzcwNzNmMmNiMzlmNjIyOGI0YThhYTQxMzU4Nzc5YzdkYWUxNiJ9.EAx6OJgIaG6YQOhjNqsIvuaaVHKe7j3Bj5RZ7d6LLlaJXL-ibiMAuZpEg5K_ifBzDjh2ygJpe6a6Gh5nnoRX3Cyfxyi3Ydkg-ZrYZiFzshTxhXG7S294vsEZPZ6WY3puz5SkB92QRzY6QovrbpuEijCjoMtHz2CYT6qjA-isUcc8UFkdPzM-Yt_xNE5a9d17yYDqVYFNk5ePUgsGKxkjPs67q8tuQBafmrS7F_G8PvbFKGsuFWjN5rsknR6rryRNzDumWrtTABHXinavLL2P_CAyFAj6hwqUokTk0jnMiKPB92TTLkbyG5Bma5m1Dpnz36d3xVaeX_cccGYPp9QOlQ

Spring Framework — Filter vs Dispatcher Servlet vs Interceptor vs Controller
https://medium.com/javarevisited/spring-framework-filter-vs-dispatcher-servlet-vs-interceptor-vs-controller-745aa34b08d8