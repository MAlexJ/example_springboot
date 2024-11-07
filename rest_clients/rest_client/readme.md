### RestClient

RestClient is a synchronous HTTP client introduced in Spring Framework 6.1 M2 that supersedes RestTemplate.
A synchronous HTTP client sends and receives HTTP requests and responses in a blocking manner,
meaning it waits for each request to complete before proceeding to the next one.

link: https://www.baeldung.com/spring-boot-restclient

#### RestClient and RestTemplate

RestTemplate, as the name suggests, is built on a template design pattern.
It’s a behavioral design pattern that defines the skeleton of an algorithm in a method, allowing subclasses
to provide specific implementations for certain steps.
While it’s a powerful pattern, it creates a need for overloading, which can be inconvenient.

To improve on this, RestClient features a fluent API. A fluent API is a design pattern that allows method chaining
in a way that makes the code more readable and expressive by sequentially calling methods on an object,
often without the need for intermediate variables.

Let’s start with creating a basic RestClient:

```
RestClient restClient = RestClient.create();
```