package com.malexj.dmdevbasespringbootcourse.example_conditional_autoconfigure.disable;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.context.annotation.Profile;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * Condition is disabled
 */
@Profile("conditional_autoconfigure_profile")
public class ConditionIsDisabled implements Condition {
    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
    return false;
    }
}
