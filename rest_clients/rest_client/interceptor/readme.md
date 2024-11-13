### RestClient Interceptor

##### 1. Defined as a Lambda Expression

RequestInterceptor is a FunctionalInterface, so we can pass a lambda expression to it

**Noted:** they work exactly same as Filters, we can modify the request before executing the chain,
response after the chain returned and if we're not satisfied with response, we can execute the chain again

code:

```
this.restClient = RestClient.builder()
        .baseUrl("http://localhost:8080")
        .requestFactory(requestFactory)
        .requestInterceptor(
                /////// <--
                (request, body, execution) -> {
                        log.info("Lambda Interceptor: modifying before sending request");
                        ClientHttpResponse response = execution.execute(request, body);
                        log.info("Lambda Interceptor: modifying after receiving response");
                        return response;
                }
                ///////
        )
        .build();
```

##### 2. Defined as a Class

**Noted:** the order is matter (like Filters as well)

                                                     request                 response
    1. Lambda Interceptor: before sending request       |                       ^
                                                        v                       |      4. Lambda Interceptor: after receiving response
                                            +----------------------------------------------+
                                            |               Lambda Interceptor             |
                                            +----------------------------------------------+
    2. Trace Interceptor: before sending request        |                       ^
                                                        v                       |      3. Trace Interceptor: after receiving response
                                            +----------------------------------------------+
                                            | TracePropagationClientHttpRequestInterceptor |
                                            +----------------------------------------------+
                                                        |                       ^
                                                        v                       |
                                            +----------------------------------------------+
                                            |               External Service               |
                                            +----------------------------------------------+


