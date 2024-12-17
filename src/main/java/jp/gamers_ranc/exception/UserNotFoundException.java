package jp.gamers_ranc.exception;

public class UserNotFoundException extends BusinessException {

    public UserNotFoundException(String email) {
        super("사용자를 찾을 수 없습니다: " + email);
    }
}
