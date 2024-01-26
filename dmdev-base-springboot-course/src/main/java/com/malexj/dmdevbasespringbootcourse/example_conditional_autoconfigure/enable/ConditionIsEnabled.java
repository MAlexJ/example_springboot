package com.malexj.dmdevbasespringbootcourse.example_conditional_autoconfigure.enable;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.context.annotation.Profile;
import org.springframework.core.type.AnnotatedTypeMetadata;

/** Condition is enable */
@Slf4j
@Profile("conditional_autoconfigure_profile")
public class ConditionIsEnabled implements Condition {

  private static final AtomicBoolean PRINT_METHOD_INFO = new AtomicBoolean(true);

  @Override
  public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
    log.info("this.hashCode() - {}", this.hashCode());
    if (PRINT_METHOD_INFO.getAndSet(false)) {
      log.info(">>>> ConditionIsEnabled: ");
      log.info(
          "1. ConfigurableListableBeanFactory - {}",
          getBeanFactoryClassName(context.getBeanFactory()));
      log.info("2. ClassLoader - {}", getBeanFactoryClassName(context.getClassLoader()));
      log.info("3.BeanDefinitionRegistry - {}", getClassName(context.getRegistry().getClass()));
      log.info("4. Environment - {}", getClassName(context.getEnvironment().getClass()));
      log.info("5. ResourceLoader - {}", getClassName(context.getResourceLoader().getClass()));
      log.info(">>>> AnnotatedTypeMetadata: ");
      log.info("1. AnnotatedTypeMetadata - {}", getClassName(metadata.getClass()));
      log.info("2. MergedAnnotations - {}", getClassName(metadata.getAnnotations().getClass()));
    }
    return true;
  }

  private Optional<String> getBeanFactoryClassName(ConfigurableListableBeanFactory beanFactory) {
    return Optional.ofNullable(beanFactory)
        .map(ConfigurableListableBeanFactory::getClass)
        .flatMap(this::optGetClassName);
  }

  private Optional<String> getBeanFactoryClassName(ClassLoader classLoader) {
    return Optional.ofNullable(classLoader)
        .map(ClassLoader::getClass)
        .flatMap(this::optGetClassName);
  }

  private Optional<String> getClassName(Class<?> aClass) {
    return Optional.ofNullable(aClass).flatMap(this::optGetClassName);
  }

  private Optional<String> optGetClassName(Class<?> aClass) {
    return Optional.ofNullable(aClass).map(Class::getName);
  }
}
