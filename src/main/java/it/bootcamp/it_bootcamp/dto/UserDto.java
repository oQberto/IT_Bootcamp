package it.bootcamp.it_bootcamp.dto;

import it.bootcamp.it_bootcamp.model.entity.enums.Role;
import it.bootcamp.it_bootcamp.validation.group.CreateAction;
import it.bootcamp.it_bootcamp.validation.annotation.LatinLetters;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
@LatinLetters(groups = CreateAction.class)
public class UserDto {

    @NotNull(groups = CreateAction.class)
    @Size(groups = CreateAction.class, max = 20, message = "Name cannot be longer than 20 characters!")
    String name;

    @NotNull(groups = CreateAction.class)
    @Size(groups = CreateAction.class, max = 40, message = "Second name cannot be longer than 40 characters!")
    String secondName;

    @NotNull(groups = CreateAction.class)
    @Size(groups = CreateAction.class, max = 40, message = "Surname cannot be longer than 40 characters!")
    String surname;

    @NotNull(groups = CreateAction.class)
    @Email(groups = CreateAction.class, regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
    @Size(groups = CreateAction.class, max = 50, message = "Email cannot be longer than 50 characters!")
    String email;

    @NotNull(groups = CreateAction.class)
    Role role;
}
