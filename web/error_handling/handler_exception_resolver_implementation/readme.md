### Spring Boot Exception Handling — HandlerExceptionResolver

Add URL and PATH configuration `.env` file

```
HTTP_DUMMY_JSON_URL=https://dummyjson.com
HTTP_DUMMY_JSON_PRODUCTS_PATH=products
```

Error and Exception handling is essential for a web application and proper responses with reasonable details
are required for better debugging and user experience.
Spring Boot provides a `/error` mapping that handles all errors in a sensible way,
and it is registered as a “global” error page in the servlet container.
If you are implementing rest API or web application with a UI template you will get a proper JSON or HTML view rendered.
You will have the option to change this template using different methods.

link: https://farzinpashaeee.medium.com/spring-boot-exception-handling-handlerexceptionresolver-11c75ffdf5d6

#### Additional property/yaml configuration

As usual, Spring Boot allows configuring these features with properties:

* **server.error.whitelabel.enabled**
  can be used to disable the Whitelabel Error Page and rely on the servlet container to provide an HTML error message

* **server.error.include-stacktrace**
  with an always value - includes the stacktrace in both the HTML and the JSON default response

* **server.error.include-message**
  since version 2.3, Spring Boot hides the message field in the response to avoid leaking sensitive information,
  we can use this property with an always value to enable it

yaml file:

```
server:
  error:
    include-message: always
```

#### First approach base on ModelAndView with status code

```
@Component
public class RestApiExceptionResolver implements HandlerExceptionResolver {

  @Override
  public ModelAndView resolveException(
      HttpServletRequest request,
      HttpServletResponse response,
      Object object,
      Exception exception) {

    ModelAndView model = new ModelAndView();
    model.setView(new MappingJackson2JsonView());
    // set status code
    model.setStatus(HttpStatus.INTERNAL_SERVER_ERROR); 
    // set error message
    model.addObject("exception", exception.getMessage());
    return model;
  }
}
```

#### Second approach base on response sendError(...) method

```
@Component
public class RestApiExceptionResolver implements HandlerExceptionResolver {

  @Override
  public ModelAndView resolveException(
      HttpServletRequest request,
      HttpServletResponse response,
      Object object,
      Exception exception) {

    try {
      response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, exception.getMessage());
    } catch (IOException e) {
      throw new ApplicationException(e);
    }

   ...........
  }
}
```