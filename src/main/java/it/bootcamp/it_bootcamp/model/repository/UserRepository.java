package it.bootcamp.it_bootcamp.model.repository;

import it.bootcamp.it_bootcamp.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
