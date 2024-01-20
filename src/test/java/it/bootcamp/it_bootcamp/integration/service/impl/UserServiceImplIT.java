package it.bootcamp.it_bootcamp.integration.service.impl;

import it.bootcamp.it_bootcamp.dto.UserDto;
import it.bootcamp.it_bootcamp.integration.IntegrationTestBase;
import it.bootcamp.it_bootcamp.mapper.UserMapper;
import it.bootcamp.it_bootcamp.model.entity.User;
import it.bootcamp.it_bootcamp.model.repository.UserRepository;
import it.bootcamp.it_bootcamp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;

import java.util.Optional;

import static it.bootcamp.it_bootcamp.model.entity.enums.Role.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.springframework.data.domain.Sort.Direction.ASC;

@RequiredArgsConstructor
public class UserServiceImplIT extends IntegrationTestBase {
    private static final PageRequest PAGE_REQUEST = PageRequest.of(0, 2, Sort.by(ASC, "email"));

    private final UserService userService;
    private final UserRepository userRepository;

    private final UserMapper userMapper;

    @Test
    @Rollback
    void createUser_shouldReturnCreatedUser() {
        UserDto user = UserDto.builder()
                .name("dummy")
                .secondName("dummy")
                .surname("dummy")
                .email("dummy@gmail.com")
                .role(ADMINISTRATOR)
                .build();

        Optional<UserDto> createdUser = userService.createUser(user);
        assertThat(createdUser).isPresent();

        Optional<User> actualUser = userRepository.findById(
                createdUser.get().getId()
        );
        assertThat(actualUser).isPresent();

        assertThat(userMapper.mapToUser(createdUser.get())).isEqualTo(actualUser.get());
    }

    @Test
    void getUsersByName_shouldReturnMatchedUsers() {
        UserDto userFilter = UserDto.builder()
                .name("Alex")
                .build();

        Page<UserDto> actualUsers = userService.getUsersBy(userFilter, PAGE_REQUEST);
        Page<Long> userIds = actualUsers.map(UserDto::getId);

        assertAll(() -> {
            assertThat(actualUsers).isNotEmpty();
            assertThat(actualUsers).hasSize(2);
            assertThat(userIds).contains(1L, 8L);
            assertThat(actualUsers).containsSequence(
                    userMapper.mapToUserDto(userRepository.findById(8L).get()),
                    userMapper.mapToUserDto(userRepository.findById(1L).get())
            );
        });
    }

    @Test
    void getUsersBySurname_shouldReturnListOfUsers() {
        UserDto userFilter = UserDto.builder()
                .surname("Alexov")
                .build();

        Page<UserDto> actualUsers = userService.getUsersBy(userFilter, PAGE_REQUEST);
        Page<Long> userIds = actualUsers.map(UserDto::getId);

        assertAll(() -> {
            assertThat(actualUsers).isNotEmpty();
            assertThat(actualUsers).hasSize(2);
            assertThat(userIds).contains(1L, 4L);
            assertThat(actualUsers).containsSequence(
                    userMapper.mapToUserDto(userRepository.findById(1L).get()),
                    userMapper.mapToUserDto(userRepository.findById(4L).get())
            );
        });
    }

    @Test
    void getUserBySecondName_shouldReturnListOfUsers() {
        UserDto userFilter = UserDto.builder()
                .secondName("Pavlovich")
                .build();

        Page<UserDto> actualUsers = userService.getUsersBy(userFilter, PAGE_REQUEST);
        Page<Long> userIds = actualUsers.map(UserDto::getId);

        assertAll(() -> {
            assertThat(actualUsers).isNotEmpty();
            assertThat(actualUsers).hasSize(1);
            assertThat(userIds).contains(2L);
        });
    }

    @Test
    void getUsersByEmail_shouldReturnTheOnlyOneUser() {
        var userFilter = UserDto.builder()
                .email("alex1@gmail.com")
                .build();

        Page<UserDto> actualUser = userService.getUsersBy(userFilter, PAGE_REQUEST);
        Page<Long> userId = actualUser.map(UserDto::getId);

        assertAll(() -> {
            assertThat(actualUser).isNotEmpty();
            assertThat(actualUser).hasSize(1);
            assertThat(userId).contains(1L);
        });
    }

    @Test
    void getUsersByRole_shouldReturnListOfUsers() {
        var userFilter = UserDto.builder()
                .role(SALE_USER)
                .build();
        var pageRequest = PageRequest.of(0, 5, Sort.by(ASC, "email"));

        Page<UserDto> actualUsers = userService.getUsersBy(userFilter, pageRequest);
        Page<Long> userIds = actualUsers.map(UserDto::getId);

        assertAll(() -> {
            assertThat(actualUsers).isNotEmpty();
            assertThat(actualUsers).hasSize(4);
            assertThat(userIds).contains(2L, 5L, 8L, 11L);
            assertThat(actualUsers).containsSequence(
                    userMapper.mapToUserDto(userRepository.findById(8L).get()),
                    userMapper.mapToUserDto(userRepository.findById(5L).get()),
                    userMapper.mapToUserDto(userRepository.findById(11L).get()),
                    userMapper.mapToUserDto(userRepository.findById(2L).get())
            );
        });
    }

    @Test
    void getUsersByAllParams_shouldReturnListOfUsers() {
        var userFilter = UserDto.builder()
                .name("Alex")
                .secondName("Alexandrovich")
                .surname("Zukov")
                .email("alex3@gmail.com")
                .role(SECURE_API_USER)
                .build();

        Page<UserDto> actualUsers = userService.getUsersBy(userFilter, PAGE_REQUEST);
        Page<Long> userIds = actualUsers.map(UserDto::getId);

        assertAll(() -> {
            assertThat(actualUsers).isNotEmpty();
            assertThat(actualUsers).hasSize(1);
            assertThat(userIds).contains(10L);
        });
    }
}
