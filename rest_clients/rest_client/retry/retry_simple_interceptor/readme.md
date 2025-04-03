### Simple Retry Interceptor

link: https://www.youtube.com/watch?v=iNWVlF8o0A4&list=PLLMxXO6kMiNiu1wcZovMFWsMw0qGv0KZS
link: https://github.com/nlinhvu/rest-client-demo-2024/blob/main/README.md

We can add retry on a Interceptor as well, for example, we're going to retry 
when receiving 429 Too Many Requests from external service.

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