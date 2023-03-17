package ru.practicum.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface UserRepository extends JpaRepository<User, Long>, UserRepositoryCustom {
    List<UserShort> findAllByEmailContainingIgnoreCase(String emailSearch);
}
