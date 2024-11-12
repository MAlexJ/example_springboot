x### RestClient

RestClient is a synchronous HTTP client introduced in Spring Framework 6.1 M2 that supersedes RestTemplate.
A synchronous HTTP client sends and receives HTTP requests and responses in a blocking manner,
meaning it waits for each request to complete before proceeding to the next one.

link: https://www.baeldung.com/spring-boot-restclient

#### New in Spring 6.1: RestClient

Spring Framework 6.1 M2 introduces the RestClient, a new synchronous HTTP client.
As the name suggests, RestClient offers the fluent API of WebClient with the infrastructure of RestTemplate

link: https://spring.io/blog/2023/07/13/new-in-spring-6-1-restclient

#### Simple Fetching With HTTP Request Methods

milar to RestTemplate, or any other rest client, RestClient allows us to make HTTP calls with request methods. Let’s
walk through different HTTP methods to create, retrieve, modify, and delete resources.

We’ll operate on an elementary Article class:

```
public class Article {
    Integer id;
    String title;
    // constructor and getters
}
```

#### Use GET to Retrieve Resources

We’ll use the GET HTTP method to request and retrieve data from a specified resource on a web server without modifying
it. It’s primarily employed for read-only operations in web applications.

To start, let’s get a simple String as the response without any serialization to our custom class:

```
String result = restClient.get()
  .uri(uriBase + "/articles")
  .retrieve()
  .body(String.class);
```

#### Use POST to Create a Resource

We’ll use the POST HTTP method to submit data to a resource on a web server, often to create new records or resources in
web applications. Unlike the GET method, which retrieves data, POST is designed for sending data to be processed by the
server, such as when submitting a web form.

```
Article article = new Article(1, "How to use RestClient");
ResponseEntity<Void> response = restClient.post()
  .uri(uriBase + "/articles")
  .contentType(APPLICATION_JSON)
  .body(article)
  .retrieve()
  .toBodilessEntity();
```

#### Use PUT to Update a Resource

```
Article article = new Article(1, "How to use RestClient even better");
ResponseEntity<Void> response = restClient.put()
  .uri(uriBase + "/articles/1")
  .contentType(APPLICATION_JSON)
  .body(article)
  .retrieve()
  .toBodilessEntity();
```