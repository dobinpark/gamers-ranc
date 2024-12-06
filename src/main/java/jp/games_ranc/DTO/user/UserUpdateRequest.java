package jp.games_ranc.DTO.user;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserUpdateRequest {
    
    @NotBlank(message = "닉네임은 필수 입력값입니다.")
    private String nickname;
    
    // 필요한 경우 추가 필드
    private String password;  // 비밀번호 변경을 원할 경우
} 