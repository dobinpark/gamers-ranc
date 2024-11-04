package jp.games_ranc.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateAccessTokenRequest {

    private String refreshToken;
}
