package jp.games_ranc.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    USER_NOT_FOUND("사용자를 찾을 수 없습니다."),
    DUPLICATE_EMAIL("이미 존재하는 이메일입니다."),
    INVALID_PASSWORD("비밀번호가 일치하지 않습니다."),
    INVALID_TOKEN("유효하지 않은 토큰입니다."),
    UNAUTHORIZED("인증되지 않은 사용자입니다.");

    private final String message;
} 