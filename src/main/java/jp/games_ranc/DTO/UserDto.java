package jp.games_ranc.DTO;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

@Data
public class UserDto {

    private String email;

    private String nickname;

    private String password;

    private String secondaryPassword;

    private String phoneNumber;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
