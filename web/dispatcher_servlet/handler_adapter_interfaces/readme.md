### HandlerAdapter Interfaces

#### General info

DispatcherServlet processes:

1. `WebApplicationContext` associated to a `DispatcherServlet` under the 
    key `DispatcherServlet.WEB_APPLICATION_CONTEXT_ATTRIBUTE` is searched for 
    and made available to all of elements of the process

2. `HandlerAdapter interface`
   The `DispatcherServlet` finds all implementations of the `HandlerAdapter interface` configured for your 
   dispatcher using `getHandler()` – each found and configured implementation handles the request 
   via handle() through the remainder of the process

3. `LocaleResolver` is optionally bound to the request to enable elements in the process to resolve the locale

4. `ThemeResolver` is optionally bound to the request to let elements, such as views, determine which theme to use

5. if a `MultipartResolver` is specified, the request is inspected for MultipartFiles – any found 
   are wrapped in a MultipartHttpServletRequest for further processing

6. `HandlerExceptionResolver` implementations declared in the WebApplicationContext picks up exceptions 
    that are thrown during processing of the request

#### 