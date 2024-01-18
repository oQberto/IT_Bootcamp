package it.bootcamp.it_bootcamp.service;

import it.bootcamp.it_bootcamp.dto.UserDto;

import java.util.Optional;

public interface UserService {

    Optional<UserDto> createUser(UserDto newUser);
}
