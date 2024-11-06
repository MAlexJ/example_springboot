### HandlerMapping

According to some criteria this bean maps oncoming requests to handlers and handler interceptors.

link: https://sivakumar-hybris-dev.medium.com/spring-framework-recap-mvc-module-864d5f89b224

#### Mappings

To understand mappings, we need to first look at how to annotate controllers since controllers are so essential
to the HandlerMapping interface.

![Handler Mapping.webp](Handler%20Mapping.webp)

#### Types

* `BeanNameUrlHandlerMapping` — handler sees bean name as URI to match

* `RouterFunctionMapping` - HandlerMapping implementation that supports RouterFunctions,
  detects one or more RouterFunction beans in the Spring configuration, combines them via RouterFunction.andOther, and
  routes requests to the resulting composed RouterFunction.

* `RequestMappingHandlerMapping` — maps methods annotated with @RequestMapping (type-level and method-level mappings)

#### Springboot properties

DispatcherServlet.properties:

```
org.springframework.web.servlet.HandlerMapping=o
    rg.springframework.web.servlet.handler.BeanNameUrlHandlerMapping,\
	org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping,\
	org.springframework.web.servlet.function.support.RouterFunctionMapping
```

#### init 

![Handler Mapping init.png](Handler%20Mapping%20init.png)

