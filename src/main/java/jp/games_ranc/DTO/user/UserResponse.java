package jp.games_ranc.DTO.user;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class UserResponse {
    private String email;
    private String nickname;
    private LocalDateTime createdAt;
    // 필요한 다른 필드들 (비밀번호 등 민감한 정보 제외)
} 