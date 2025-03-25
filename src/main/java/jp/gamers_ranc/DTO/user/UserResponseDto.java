package jp.gamers_ranc.DTO.user;

import jp.gamers_ranc.Entity.user.User;
import jp.gamers_ranc.Entity.user.UserRole;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UserResponseDto {
    private Long id;
    private String email;
    private String nickname;
    private String phoneNumber;
    private Long point;
    private UserRole role;

    @Builder
    public UserResponseDto(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.nickname = user.getNickname();
        this.phoneNumber = user.getPhoneNumber();
        this.point = user.getPoint();
        this.role = user.getRole();
    }
}
