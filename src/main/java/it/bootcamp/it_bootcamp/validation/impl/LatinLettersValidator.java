package it.bootcamp.it_bootcamp.validation.impl;

import it.bootcamp.it_bootcamp.dto.UserDto;
import it.bootcamp.it_bootcamp.validation.annotation.LatinLetters;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

@Component
public class LatinLettersValidator implements ConstraintValidator<LatinLetters, UserDto> {

    @Override
    public boolean isValid(UserDto value, ConstraintValidatorContext context) {
        var pattern = "[a-zA-Z]*";

        return value.getName().matches(pattern)
               && value.getSecondName().matches(pattern)
                && value.getSurname().matches(pattern);
    }
}
