### @RequestParam

You can use the @RequestParam annotation to bind Servlet request parameters (that is, query parameters or form data) 
to a method argument in a controller.

```
@GetMapping
	public String setupForm(@RequestParam("petId") int petId, Model model) { 
		...........
	}
```

Using @RequestParam to bind petId.

By default, method parameters that use this annotation are required, 
but you can specify that a method parameter is optional by setting the @RequestParam annotation’s required flag to false 
or by declaring the argument with a java.util.Optional wrapper.

Declaring the argument type as an array or list allows for resolving multiple parameter values for the same parameter name.

When an @RequestParam annotation is declared as a Map<String, String> or MultiValueMap<String, String>, 
without a parameter name specified in the annotation, 
then the map is populated with the request parameter values for each given parameter name. 
The following example shows how to do so with form data processing:

```
@PostMapping(path = "/process", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public String processForm(@RequestParam MultiValueMap<String, String> params) {
		// ...
	}
```

link: https://docs.spring.io/spring-framework/reference/web/webmvc/mvc-controller/ann-methods/requestparam.html 