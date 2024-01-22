package it.bootcamp.it_bootcamp.service.impl;

import it.bootcamp.it_bootcamp.dto.UserDto;
import it.bootcamp.it_bootcamp.mapper.UserMapper;
import it.bootcamp.it_bootcamp.model.repository.UserRepository;
import it.bootcamp.it_bootcamp.service.UserService;
import it.bootcamp.it_bootcamp.service.exception.UserCreationException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static it.bootcamp.it_bootcamp.service.util.QueryUtil.filterBy;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    @Transactional
    public UserDto createUser(UserDto newUser) {
        return Optional.of(newUser)
                .map(userMapper::mapToUser)
                .map(userRepository::saveAndFlush)
                .map(userMapper::mapToUserDto)
                .orElseThrow(() -> new UserCreationException("Couldn't create a user!"));
    }

    @Override
    public Page<UserDto> getUsersBy(UserDto dto, Pageable pageable) {
        return userRepository.findAll(filterBy(dto), pageable)
                .map(userMapper::mapToUserDto);
    }
}
