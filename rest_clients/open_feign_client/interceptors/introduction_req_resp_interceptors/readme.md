### Introduction to Interceptors OpenFeign

1. For request: implements feign.RequestInterceptor
2. For response: implements feign.ResponseInterceptor

#### Application configuration

Add property to .env file for basic auth

``
HTTP_BIN_URL=http://httpbin.org
HTTP_BIN_BASIC_AUTH_PATH_AUTH=/basic-auth
HTTP_BIN_BASIC_AUTH_PATH_BEARER=/bearer
HTTP_BIN_BASIC_AUTH_USER=......
HTTP_BIN_BASIC_AUTH_PASSWORD=......
``

#### Interceptors configuration

1. First case:

Create OpenFeign interception class with configuration in application.yaml file

* Interceptor class:
  ```
  @Component
  public class AuthOpenFeignInterception implements RequestInterceptor {
  
    private static final String BASIC_TOKEN_PREFIX = "Basic %s";
    
    private static final String TOKEN_PREFIX = "%s:%s";
  
    @Value("${service.client.user}")
    private String username;
  
    @Value("${service.client.password}")
    private String password;
  
    @Override
    public void apply(RequestTemplate requestTemplate) {
      var authorizationToken = buildToken();
      requestTemplate.header("Authorization", String.format(BASIC_TOKEN_PREFIX, authorizationToken));
      requestTemplate.header("Accept", APPLICATION_JSON_VALUE);
    }
  
    private String buildToken() {
      var encoder = Base64.getEncoder();
      byte[] token = String.format(TOKEN_PREFIX, username, password).getBytes();
      return encoder.encodeToString(token);
    }
  }
  ```

* Add information about interceptors to application.yaml file
  ```
  spring:
    cloud:
      openfeign:
        client:
          config:
            authOpenFeignWebclient:
              connectTimeout: 5000
              readTimeout: 5000
              loggerLevel: full
              requestInterceptors: com.malex.inroduction.auth.AuthOpenFeignInterception  
  ```

or

2. Second case:

Apply configuration to OpenFeign client interface

* Add configuration class as @FeignClient annotation 'configuration={java.class}' parameters
    ```
    @FeignClient(
        name = "myOpenFeignWebclient",
        url = "${service.client.url}",
        configuration = OpenFeignInterceptionConfig.class)
    public interface OpenFeignWebclient {
    
      @GetMapping("/${service.client.user}/${service.client.password}")
      IntroductionApplication.UserResponse gerInfo();
    }
    ```

link: https://www.baeldung.com/spring-cloud-openfeign#interceptors