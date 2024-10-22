package jp.games_ranc.DTO;

import lombok.Data;

@Data
public class LoginRequestDto {

    private String username;

    private String password;

    private String secondaryPassword;

    private String nickname;

    private String phoneNumber;
}
