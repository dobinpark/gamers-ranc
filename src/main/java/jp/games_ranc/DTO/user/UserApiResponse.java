package jp.games_ranc.DTO.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserApiResponse {

    private boolean success;

    private String message;
}
