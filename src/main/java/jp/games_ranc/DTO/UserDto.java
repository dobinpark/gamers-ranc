package jp.games_ranc.DTO;

import lombok.Data;

@Data
public class UserDto {

    private String email;

    private String nickname;

    private String password;

    private String secondaryPassword;

    private String phoneNumber;
}
