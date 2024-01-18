package it.bootcamp.it_bootcamp.controller.rest;

import it.bootcamp.it_bootcamp.dto.UserDto;
import it.bootcamp.it_bootcamp.service.UserService;
import it.bootcamp.it_bootcamp.service.exception.UserCreationException;
import it.bootcamp.it_bootcamp.validation.group.CreateAction;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/users")
public class UserRestController {
    private final UserService userService;

    @PostMapping("/create")
    public ResponseEntity<UserDto> createUser(@ModelAttribute("createUser") @Validated(CreateAction.class) UserDto dto) {
        return ResponseEntity.ok(
                userService.createUser(dto)
                        .orElseThrow(() -> new UserCreationException("Couldn't create a user!"))
        );
    }
}