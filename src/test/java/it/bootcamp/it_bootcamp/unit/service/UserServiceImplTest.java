package it.bootcamp.it_bootcamp.unit.service;

import it.bootcamp.it_bootcamp.dto.UserDto;
import it.bootcamp.it_bootcamp.mapper.UserMapper;
import it.bootcamp.it_bootcamp.model.entity.User;
import it.bootcamp.it_bootcamp.model.repository.UserRepository;
import it.bootcamp.it_bootcamp.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;

import static it.bootcamp.it_bootcamp.model.entity.enums.Role.ADMINISTRATOR;
import static it.bootcamp.it_bootcamp.model.entity.enums.Role.CUSTOMER_USER;
import static it.bootcamp.it_bootcamp.service.util.QueryUtil.filterBy;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void createUser_shouldReturnCreatedUser() {
        when(userMapper.mapToUser(getUserDto()))
                .thenReturn(getUser());
        when(userRepository.saveAndFlush(getUser()))
                .thenReturn(getUser());
        when(userMapper.mapToUserDto(getUser()))
                .thenReturn(getUserDto());

        UserDto actualUser = userService.createUser(getUserDto());

        assertAll(() -> {
            assertThat(actualUser).isNotNull();
            assertThat(actualUser).isEqualTo(getUserDto());
        });
    }

    @Test
    void getUsersBy_shouldReturnUsers() {
        UserDto requestedUser = UserDto.builder()
                .name("Sam")
                .secondName("Petrovich")
                .surname("Samov")
                .build();
        PageRequest pageRequest = PageRequest.of(0, 2);

        when(userRepository.findAll(filterBy(requestedUser), pageRequest))
                .thenReturn(getListOfUsers());

        Page<UserDto> actualResult = userService.getUsersBy(requestedUser, pageRequest);

        assertAll(() -> {
            assertThat(actualResult).isNotEmpty();
            assertThat(actualResult).hasSize(2);
        });
    }

    private UserDto getUserDto() {
        return UserDto.builder()
                .id(1L)
                .name("dummy")
                .secondName("dummy")
                .surname("dummy")
                .email("dummy@gmail.com")
                .role(ADMINISTRATOR)
                .build();
    }

    private User getUser() {
        return User.builder()
                .id(1L)
                .name("dummy")
                .secondName("dummy")
                .surname("dummy")
                .email("dummy@gmail.com")
                .role(ADMINISTRATOR)
                .build();
    }

    private Page<User> getListOfUsers() {
        return new PageImpl<>(List.of(
                new User(1L, "Sam", "Petrovich", "Samov", "sam1@gmail.com", CUSTOMER_USER),
                new User(2L, "Sam", "Petrovich", "Samov", "sam2@gmail.com", ADMINISTRATOR)
        ));
    }
}