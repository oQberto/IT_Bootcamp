package it.bootcamp.it_bootcamp.service.util;

import com.querydsl.core.types.Predicate;
import it.bootcamp.it_bootcamp.dto.UserDto;
import it.bootcamp.it_bootcamp.dto.querydsl.QPredicate;
import lombok.experimental.UtilityClass;

import static it.bootcamp.it_bootcamp.model.entity.QUser.user;

@UtilityClass
public class QueryUtil {

   public static Predicate filterBy(UserDto dto) {
       return QPredicate.builder()
               .add(dto.getName(), user.name::contains)
               .add(dto.getSecondName(), user.secondName::contains)
               .add(dto.getSurname(), user.surname::contains)
               .add(dto.getEmail(), user.email::contains)
               .add(dto.getRole(), user.role::eq)
               .orderBy(user.email.asc())
               .build();
    }
}
