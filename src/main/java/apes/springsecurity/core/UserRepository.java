package apes.springsecurity.core;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query("select distinct u from User u " +
            "join fetch u.userAuthorities ua " +
            "join fetch ua.authority " +
            "where u.username = :username")
    Optional<User> findByUsernameWithAuthorities(@Param("username") String username);
}
