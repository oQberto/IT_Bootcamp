package it.bootcamp.it_bootcamp.mapper;

import it.bootcamp.it_bootcamp.dto.UserDto;
import it.bootcamp.it_bootcamp.model.entity.User;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Component
@Mapper(componentModel = SPRING)
public interface UserMapper {

    UserDto mapToUserDto(User user);

    User mapToUser(UserDto dto);
}
