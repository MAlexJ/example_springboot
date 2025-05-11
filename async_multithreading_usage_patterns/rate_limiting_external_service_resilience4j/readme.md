### Limit the number of threads to call an external service

link: https://habr.com/ru/articles/838402/

#### Getting Started

Getting started with resilience4j-spring-boot2 or resilience4j-spring-boot3

link: https://resilience4j.readme.io/docs/getting-started-3

1. Add dependencies:

```
dependencies {
  implementation mentation "io.github.resilience4j:resilience4j-spring-boot3:${resilience4jVersion}"
  implementation "org.springframework.boot:spring-boot-starter-actuator"
  implementation "org.springframework.boot:spring-boot-starter-aop"
}
```

2. Add configuration to application yaml file

```
resilience4j.bulkhead:
    instances:
        backendA:
            maxConcurrentCalls: 10
        backendB:
            maxWaitDuration: 10ms
            maxConcurrentCalls: 20
```