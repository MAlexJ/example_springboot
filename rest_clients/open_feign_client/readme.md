### Spring Cloud OpenFeign — a declarative REST client for Spring Boot apps

The above class contains these beans:

* Decoder – ResponseEntityDecoder, which wraps SpringDecoder, used to decode the Response
* Encoder – SpringEncoder is used to encode the RequestBody.
* Logger – Slf4jLogger is the default logger used by Feign.
* Contract – SpringMvcContract, which provides annotation processing
* Feign-Builder – HystrixFeign.Builder is used to construct the components.
* Client – LoadBalancerFeignClient or default Feign client

link: https://www.baeldung.com/spring-cloud-openfeign

Feign makes writing web service clients easier with pluggable annotation support, which includes Feign annotations and
JAX-RS annotations.

Also, Spring Cloud adds support for Spring MVC annotations and for using the same HttpMessageConverters as used in
Spring Web.

One great thing about using Feign is that we don’t have to write any code for calling the service, other than an
interface definition.

### Feign

Feign — a declarative HTTP client developed by Netflix.

Feign aims at simplifying HTTP API clients. Simply put, the developer needs only to declare and annotate
an interface while the actual implementation is provisioned at runtime.

link: https://www.baeldung.com/intro-to-feign