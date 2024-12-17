package jp.gamers_ranc.DTO.user;

import jp.gamers_ranc.Entity.user.User;
import jp.gamers_ranc.Entity.user.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class UserResponse {

    private Long id;
    private String email;
    private String nickname;
    private Long point;
    private UserRole role;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static UserResponse from(User user) {
        return new UserResponse(
                user.getId(),
                user.getEmail(),
                user.getNickname(),
                user.getPoint(),
                user.getRole(),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }
}
