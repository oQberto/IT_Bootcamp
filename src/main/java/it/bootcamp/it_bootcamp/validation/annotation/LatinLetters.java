package it.bootcamp.it_bootcamp.validation.annotation;

import it.bootcamp.it_bootcamp.validation.impl.LatinLettersValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = LatinLettersValidator.class)
public @interface LatinLetters {

    String message() default "The field must contain only Latin letters!";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}