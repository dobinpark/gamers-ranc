package jp.gamers_ranc.DTO.user;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserUpdateRequest {

    private String nickname;
    private String password;
}
