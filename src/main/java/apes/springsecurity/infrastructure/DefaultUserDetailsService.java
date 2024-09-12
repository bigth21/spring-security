package apes.springsecurity.infrastructure;

import apes.springsecurity.core.persistence.Account;
import apes.springsecurity.core.persistence.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

@RequiredArgsConstructor
public class DefaultUserDetailsService implements UserDetailsService {
    private final AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Account> byUsername = accountRepository.findByUsernameWithAuthorities(username);
        if (byUsername.isEmpty())
            throw new UsernameNotFoundException(username);

        Account account = byUsername.get();
        return new DefaultUserDetails(
                account.getUsername(),
                account.getPassword(),
                account.getUser().getAuthorities()
                        .stream()
                        .map(authority -> new SimpleGrantedAuthority(authority.getName().name()))
                        .toList());
    }
}
