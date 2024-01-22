package it.bootcamp.it_bootcamp.api.controller.rest;

import it.bootcamp.it_bootcamp.dto.UserDto;
import it.bootcamp.it_bootcamp.service.UserService;
import it.bootcamp.it_bootcamp.validation.group.CreateAction;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static org.springframework.data.domain.Sort.Direction.ASC;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/users")
public class UserRestController {
    private final UserService userService;

    @PostMapping("/create")
    public ResponseEntity<UserDto> createUser(@ModelAttribute("createUser") @Validated(CreateAction.class) UserDto dto) {
        return ResponseEntity.ok(
                userService.createUser(dto)
        );
    }

    @GetMapping
    public ResponseEntity<Page<UserDto>> getUsers(UserDto dto,
                                                  @PageableDefault(sort = "email", direction = ASC) Pageable pageable) {
        return ResponseEntity.ok(
                userService.getUsersBy(dto, pageable)
        );
    }
}