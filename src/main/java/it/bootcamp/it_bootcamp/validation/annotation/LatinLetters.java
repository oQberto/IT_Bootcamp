package it.bootcamp.it_bootcamp.validation.annotation;

import it.bootcamp.it_bootcamp.validation.group.CreateAction;
import jakarta.validation.constraints.Pattern;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({FIELD})
@Retention(RUNTIME)
@Pattern(groups = CreateAction.class, regexp = "[a-zA-Z]")
public @interface LatinLetters {

    String message() default "The field must contain only Latin letters!";
}