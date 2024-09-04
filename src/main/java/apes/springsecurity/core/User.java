package apes.springsecurity.core;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private boolean enabled;

    @OneToMany(mappedBy = "user")
    private List<UserAuthority> userAuthorities;

    public User(boolean enabled) {
        this.enabled = enabled;
    }

    public List<Authority> getAuthorities() {
        return userAuthorities.stream()
                .map(UserAuthority::getAuthority)
                .toList();
    }
}
