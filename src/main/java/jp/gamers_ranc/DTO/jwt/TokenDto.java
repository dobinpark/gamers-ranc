package jp.gamers_ranc.DTO.jwt;

import lombok.Builder;
import lombok.Getter;

@Getter
public class TokenDto {
    private String token;
    private String email;

    @Builder
    public TokenDto(String token, String email) {
        this.token = token;
        this.email = email;
    }
}
