package apes.springsecurity.core;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(indexes = @Index(name = "ix_userid_authoriyid", columnList = "user, authority", unique = true))
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class UserAuthority {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private User user;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "authoriyId", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Authority authority;
}
