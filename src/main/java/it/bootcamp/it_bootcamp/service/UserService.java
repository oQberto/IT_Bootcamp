package it.bootcamp.it_bootcamp.service;

import it.bootcamp.it_bootcamp.dto.UserDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface UserService {

    Optional<UserDto> createUser(UserDto newUser);

    Page<UserDto> getUsersBy(UserDto dto, Pageable pageable);
}
