package jp.games_ranc.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserDTO {

    private String id;

    private String password;

    private String email;

    private String nickName;

    private String phoneNumber;

    @JsonFormat(pattern = "yyyy.MM.dd/HH:mm/E")
    private LocalDateTime localDateTime;
}
