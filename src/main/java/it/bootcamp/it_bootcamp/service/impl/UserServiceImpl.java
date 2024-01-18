package it.bootcamp.it_bootcamp.service.impl;

import it.bootcamp.it_bootcamp.dto.UserDto;
import it.bootcamp.it_bootcamp.mapper.UserMapper;
import it.bootcamp.it_bootcamp.model.repository.UserRepository;
import it.bootcamp.it_bootcamp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    @Transactional
    public Optional<UserDto> createUser(UserDto newUser) {
        return Optional.of(newUser)
                .map(userMapper::mapToUser)
                .map(userRepository::saveAndFlush)
                .map(userMapper::mapToUserDto);
    }
}
