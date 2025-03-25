package jp.gamers_ranc.DTO.user;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserUpdateRequest {

    @Size(min = 2, max = 20, message = "닉네임은 2자 이상 20자 이하여야 합니다.")
    private String nickname;

    @Size(min = 8, message = "비밀번호는 최소 8자 이상이어야 합니다.")
    private String password;

    private String passwordConfirm;

    @Pattern(regexp = "^\\d{11}$", message = "전화번호는 11자리 숫자로 입력해주세요. (예: 01012345678)")
    private String phoneNumber;

    @Builder
    public UserUpdateRequest(String nickname, String password, String passwordConfirm, String phoneNumber) {
        this.nickname = nickname;
        this.password = password;
        this.passwordConfirm = passwordConfirm;
        this.phoneNumber = phoneNumber;
    }
}
