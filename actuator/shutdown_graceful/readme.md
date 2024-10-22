### Graceful

Console logs inputs:

```angular2html
INFO [shutdown-graceful] [Thread-1] o.s.b.w.e.tomcat.GracefulShutdown : Commencing graceful shutdown.
Waiting for active requests to complete
INFO [tomcat-shutdown] o.s.b.w.e.tomcat.GracefulShutdown : Graceful shutdown complete
```

#### Requirements for Graceful Shutdown

The shutdown of the Spring Boot application can happen either in the form of a crash or a manually initiated one.

You might be wondering when we need to manually initiate one.
This requirement comes in many events, some of which include:

* When we are shutting down services for any maintenance activities.
* When we are performing rolling update deployment.

In the above cases, either we kill the process or achieve the same using the shutdown method provided
by the Spring Boot Actuator.

link: https://medium.com/@contact.technovisionconsulting/how-to-achieve-a-graceful-shutdown-in-spring-boot-ec93d55916ed

#### 1. Add dependencies

```
    implementation 'org.springframework.boot:spring-boot-starter-actuator'
    implementation 'org.springframework.boot:spring-boot-starter-web'
```

#### 2. Actuator configuration

Enabling shutdown method

Along with this we also need to add the following application.properties to enable the endpoint.
By default, the actuator endpoints are disabled for security reasons.

```
management:
  endpoint:
    shutdown:
      enabled: true
  endpoints:
    web:
      exposure:
        include: info,health,shutdown
```

#### 3. Graceful configuration

Spring Boot version 2.3 has introduced a few settings in application.properties / yaml,
which help in achieving Graceful shutdown.
Let’s see those properties.

```
spring:
  application:
    name: shutdown-graceful

  lifecycle:
    timeout-per-shutdown-phase: 1m

server:
  shutdown: graceful
```

With this setting, when you stop the server, it won’t accept new requests, ensuring a smooth shutdown process.

By default, it will wait for 30 seconds for any pending requests to complete.
Is it possible to control this time? I mean increase or decrease

Yes, Spring Boot provides one more property with which we can control the amount of time given
for the Shutdown process:

``spring.lifecycle.timeout-per-shutdown-phase=1m``

#### 4. Shutdown server by url

```
###
POST http://localhost:8080/actuator/shutdown
```