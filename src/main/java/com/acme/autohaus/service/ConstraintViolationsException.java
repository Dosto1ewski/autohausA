package com.acme.autohaus.service;

import com.acme.autohaus.entity.Autohaus;
import jakarta.validation.ConstraintViolation;
import java.util.Collection;
import lombok.Getter;

/**
 * Exception, falls es mindestens ein verletztes Constraint gibt.
 */
@Getter
public class ConstraintViolationsException extends RuntimeException {
    /**
     * Die verletzten Constraints.
     */
    private final transient Collection<ConstraintViolation<Autohaus>> violations;

    ConstraintViolationsException(
        @SuppressWarnings("ParameterHidesMemberVariable")
        final Collection<ConstraintViolation<Autohaus>> violations
    ) {
        super("Constraints sind verletzt");
        this.violations = violations;
    }
}
