package apes.springsecurity.core;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Table(indexes = @Index(name = "ix_username", columnList = "username", unique = true))
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String username;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private boolean enabled;

    @OneToMany(mappedBy = "user")
    private List<UserAuthority> userAuthorities;

    public List<Authority> getAuthorities() {
        return userAuthorities.stream()
                .map(UserAuthority::getAuthority)
                .toList();
    }
}
