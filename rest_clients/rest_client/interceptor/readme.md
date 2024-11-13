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

##### 3. Simple Retry Interceptor

code:

```
public class RetryClientHttpRequestInterceptor implements ClientHttpRequestInterceptor {
    private static final Logger log = LoggerFactory.getLogger(RetryClientHttpRequestInterceptor.class);

    private int attempts = 3;
    private Set<HttpStatus> retryableStatus = Set.of(HttpStatus.TOO_MANY_REQUESTS);

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        for (int i = 0; i < this.attempts; i++) {
            ClientHttpResponse response = execution.execute(request, body);
            if (!retryableStatus.contains(response.getStatusCode())) {
                log.info("Successful at the %d attempts".formatted(i+1));
                return response;
            }

            log.info("%d attempts: %s".formatted(i+1, Instant.now()));
        }
        log.error("Retry exhausted!");
        throw new IllegalStateException("Exceed number of retries");
    }
}
```