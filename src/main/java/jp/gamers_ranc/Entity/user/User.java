package jp.gamers_ranc.Entity.user;

import jakarta.persistence.*;
import jp.gamers_ranc.Entity.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String nickname;

    @Column(columnDefinition = "bigint default 0")
    private Long point = 0L;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole role = UserRole.USER; // 기본값은 일반 사용자

    @Builder
    public User(String email, String password, String nickname, UserRole role) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.role = UserRole.USER;
    }

    public void update(String nickname, String password) {
        if (nickname != null) this.nickname = nickname;
        if (password != null) this.password = password;
    }

    public void addPoint(Long amount) {
        this.point += amount;
    }

    public void subtractPoint(Long amount) {
        this.point = Math.max(0, this.point - amount);
    }

    public boolean isAdmin() {
        return this.role == UserRole.ADMIN;
    }
}
