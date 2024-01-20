package it.bootcamp.it_bootcamp.integration.annotation;

import it.bootcamp.it_bootcamp.config.TestApplicationRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target(TYPE)
@Retention(RUNTIME)
@Transactional
@SpringBootTest(classes = TestApplicationRunner.class)
public @interface IT {
}
