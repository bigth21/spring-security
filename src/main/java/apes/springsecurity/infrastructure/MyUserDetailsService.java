package apes.springsecurity.infrastructure;

import apes.springsecurity.core.User;
import apes.springsecurity.core.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

@RequiredArgsConstructor
public class MyUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> byUsername = userRepository.findByUsernameWithAuthorities(username);
        if (byUsername.isEmpty())
            throw new UsernameNotFoundException(username);
        return new MyUserDetails(byUsername.get());
    }
}
