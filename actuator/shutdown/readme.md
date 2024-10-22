### Actuator shutdown (shutdown endpoint)

The shutdown endpoint is used to shut down the application.

link: https://docs.spring.io/spring-boot/api/rest/actuator/shutdown.html
link: https://www.baeldung.com/spring-boot-shutdown

#### Configuration app

Add maven dependency:

```
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
```

or gradle:

```
    implementation 'org.springframework.boot:spring-boot-starter-actuator'
    implementation 'org.springframework.boot:spring-boot-starter-web'
```

Finally, we’ll enable the shutdown endpoint in the `application.properties` file:

```
management.endpoints.web.exposure.include=*

// or management.endpoints.web.exposure.include=shutdown
management.endpoint.shutdown.enabled=true

// 
endpoints.shutdown.enabled=true
```

or `application.yaml` file:

```
management:
  endpoint:
    shutdown:
      enabled: true
  endpoints:
    web:
      exposure:
        include: '*'
```

#### Shutting Down the Application

To shut down the application, make a POST request to `/actuator/shutdown`, as shown in the following curl-based example:

```
$ curl 'http://localhost:8080/actuator/shutdown' -i -X POST
```

A response similar to the following is produced:

```
HTTP/1.1 200 OK
Content-Type: application/vnd.spring-boot.actuator.v3+json
Content-Length: 41

{
"message" : "Shutting down, bye..."
}
```

#### Response Structure

The response contains details of the result of the shutdown request.

The following table describes the structure of the response:

* Path :message
* Type: String
* Description: Message describing the result of the request.