package jp.games_ranc.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddUserRequest {

    private String email;

    private String nickname;

    private String password;
}
