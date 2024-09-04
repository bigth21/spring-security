package apes.springsecurity.core.service;

import apes.springsecurity.core.persistence.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserAuthorityRepository userAuthorityRepository;
    private final AuthorityRepository authorityRepository;

    @Transactional
    public User createUser(String username, String password, List<AuthorityName> authorityNames) {
        Optional<Account> byUsername = accountRepository.findByUsername(username);
        if (byUsername.isPresent())
            throw new UsernameAlreadyExistException(username);

        User user = userRepository.save(new User(true));
        accountRepository.save(new Account(user, username, passwordEncoder.encode(password)));
        List<UserAuthority> userAuthorities = authorityNames.stream()
                .map(name -> authorityRepository.findByName(name)
                        .orElseThrow())
                .map(authority -> new UserAuthority(user, authority))
                .toList();
        userAuthorityRepository.saveAll(userAuthorities);
        return user;
    }

}
