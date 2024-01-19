package it.bootcamp.it_bootcamp.controller.rest;

import it.bootcamp.it_bootcamp.dto.UserDto;
import it.bootcamp.it_bootcamp.service.UserService;
import it.bootcamp.it_bootcamp.service.exception.UserCreationException;
import it.bootcamp.it_bootcamp.validation.group.CreateAction;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/users")
    public ResponseEntity<Page<UserDto>> getUsers(@ModelAttribute("filter") UserDto dto,
                                         Pageable pageable) {
        return ResponseEntity.ok(
                userService.getUsersBy(dto, pageable)
        );
    }
}