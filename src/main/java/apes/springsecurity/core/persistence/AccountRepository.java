package apes.springsecurity.core.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
    @Query("select distinct a from Account a " +
            "join fetch a.user u " +
            "join fetch u.userAuthorities ua " +
            "join fetch ua.authority " +
            "where a.username = :username")
    Optional<Account> findByUsernameWithUserAndAuthorities(@Param("username") String username);

    Optional<Account> findByUsername(String username);
}
