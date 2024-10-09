### Rate Limiting with AOP and ConcurrentHashMap

Rate limiting is an architectural tactic for a server to limit access to an API.

It helps to:

* protect against server overload due to clients that call the server in a short time frame too often
* increase the fairness of how clients use server resources
* allow pricing schemes for different amounts of requests

#### The idea

We define an annotation that you can add to any HTTP endpoint that should have a rate limit protection.
We define an aspect for methods with that annotation that counts HTTP requests per sender IP address.
If the rate limit is exceeded we throw an exception.
In the exception handling we return an HTTP Status code of 429.
The rate limit configuration will be possible using properties in the Spring configuration file.

link: https://www.innoq.com/en/blog/2024/02/rate-limiting-with-spring-boot/

### Detecting the rate limit

We start by writing an annotation @WithRateLimitProtection that allows to mark HTTP endpoints like the above
processRequest one that should have a rate limit protection.

We define the annotation like this:

```
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface WithRateLimitProtection {
}
```

If the rate limit is exceeded at the endpoint, a RateLimitException should be thrown that we define like this:

```
@ResponseStatus(value = HttpStatus.TOO_MANY_REQUESTS)
public class RateLimitException extends RuntimeException {

    public RateLimitException(final String message) {
        super(message);
    }

    public ApiErrorMessage toApiErrorMessage(final String path) {
        return new ApiErrorMessage(HttpStatus.TOO_MANY_REQUESTS.value(), HttpStatus.TOO_MANY_REQUESTS.name(), this.getMessage(), path);
    }
}
```

where ApiErrorMessage will be translated to a JSON body in the response, such that our JSON API answers with JSON also
in case of error and not with the Spring default, i.e. an HTML page:

```
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ApiErrorMessage {

    private final UUID          id        = UUID.randomUUID();
    private final int           status;
    private final String        error;
    private final String        message;
    private final LocalDateTime timestamp = LocalDateTime.now(Clock.systemUTC());
    private final String        path;
    ......
    }
```

Let’s define an aspect that implements the rate limiting using Spring AOP.
The aspect is called before the marked endpoint method is called:

```
@Aspect
@Component
public class RateLimitAspect {

    public static final String ERROR_MESSAGE = "To many request at endpoint %s from IP %s! Please try again after %d milliseconds!";
    private final ConcurrentHashMap<String, List<Long>> requestCounts = new ConcurrentHashMap<>();

    @Value("${APP_RATE_LIMIT:#{200}}")
    private int rateLimit;

    @Value("${APP_RATE_DURATIONINMS:#{60000}}")
    private long rateDuration;

    /**
     * Executed by each call of a method annotated with {@link WithRateLimitProtection} which should be an HTTP endpoint.
     * Counts calls per remote address. Calls older than {@link #rateDuration} milliseconds will be forgotten. If there have
     * been more than {@link #rateLimit} calls within {@link #rateDuration} milliseconds from a remote address, a {@link RateLimitException}
     * will be thrown.
     * @throws RateLimitException iff rate limit for a given remote address has been exceeded
     */
    @Before("@annotation(com.innoq.test.WithRateLimitProtection)")
    public void rateLimit() {
        final ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        final String key = requestAttributes.getRequest().getRemoteAddr();
        final long currentTime = System.currentTimeMillis();
        requestCounts.putIfAbsent(key, new ArrayList<>());
        requestCounts.get(key).add(currentTime);
        cleanUpRequestCounts(currentTime);
        if (requestCounts.get(key).size() > rateLimit) {
            throw new RateLimitException(String.format(ERROR_MESSAGE, requestAttributes.getRequest().getRequestURI(), key, rateDuration));
        }
    }

    private void cleanUpRequestCounts(final long currentTime) {
        requestCounts.values().forEach(l -> {
            l.removeIf(t -> timeIsTooOld(currentTime, t));
        });
    }

    private boolean timeIsTooOld(final long currentTime, final long timeToCheck) {
        return currentTime - timeToCheck > rateDuration;
    }
}
```

Handling Rate Limit Exceptions
So far, having written the code above, a RateLimitException will be thrown if there are too many requests.
We need to add exception handling in Spring that translates the thrown RateLimitException to an HTTP 429 response with
some information on the error in its response body.

For that, we write an exception handler:

```
@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class RateLimitExceptionHandler {
    private static final Logger LOG = LoggerFactory.getLogger(RateLimitExceptionHandler.class);
    @ExceptionHandler(RateLimitException.class)
    public ResponseEntity<ApiErrorMessage> handleInvalidFieldsInValidJson(final RateLimitException rateLimitException, final HttpServletRequest request) {
        final ApiErrorMessage apiErrorMessage = rateLimitException.toApiErrorMessage(request.getRequestURI());
        logIncomingCallException(rateLimitException, apiErrorMessage);
        return new ResponseEntity<>(apiErrorMessage, HttpStatus.TOO_MANY_REQUESTS);
    }

    private static void logIncomingCallException(final RateLimitException rateLimitException, final ApiErrorMessage apiErrorMessage) {
        LOG.error(String.format("%s: %s", apiErrorMessage.getId(), rateLimitException.getMessage()), rateLimitException);
    }
}
```
