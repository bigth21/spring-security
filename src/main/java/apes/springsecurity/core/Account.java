package apes.springsecurity.core;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(indexes = {
        @Index(name = "ix_username", columnList = "username", unique = true),
        @Index(name = "ix_userid", columnList = "user")})
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Account {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private User user;
    @Column(nullable = false)
    private String username;
    @Column(nullable = false)
    private String password;

    public Account(User user, String username, String password) {
        this.user = user;
        this.username = username;
        this.password = password;
    }
}
