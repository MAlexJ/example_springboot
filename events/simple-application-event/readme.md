### Spring Events

link: https://www.baeldung.com/spring-events

##### Guidelines

There are a few simple guidelines to follow:

1. The event class should extend ApplicationEvent if we’re using versions before Spring Framework 4.2. As of the 4.2
   version, the event classes no longer need to extend the ApplicationEvent class


2. The publisher should inject <b>org.springframework.context.ApplicationEventPublisher</b> or <b>implement
   org.springframework.context.ApplicationEventPublisherAware</b>

* inject org.springframework.context.ApplicationEventPublisher

```java

@Component
@RequiredArgsConstructor
public class SimpleSpringEventPublisher {

    private final ApplicationEventPublisher applicationEventPublisher;

    public void publishEvent(Object event, String id) {
        applicationEventPublisher.publishEvent(new SimpleApplicationEvent(this, event, id));
    }
}

```

* implement org.springframework.context.ApplicationEventPublisherAware

```java

@Component
public class CustomSpringEventPublisher implements ApplicationEventPublisherAware {

    private ApplicationEventPublisher applicationEventPublisher;

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public void publishEvent(final String message) {
        applicationEventPublisher.publishEvent(customSpringEvent);
    }
}
```

3. The listener should implement the ApplicationListener<...event> interface or add annotation @EventListener

* implement the ApplicationListener<...event>

```java

@Slf4j
@Component
public class SimpleSpringEventListener implements ApplicationListener<SimpleApplicationEvent> {

    @Override
    public void onApplicationEvent(SimpleApplicationEvent event) {
        log.info("<<<< Handle simple event - {}, id -{}", event.getEvent(), event.getId());
    }
}
```

* @EventListener annotation

```java

@Slf4j
@Component
public class CustomSpringEventListener {

    @EventListener
    public void handleCustomSpringEvent(CustomSpringEvent event) {
        log.info("<<<< Handle custom event - {}", event.getMessage());
    }
}
```