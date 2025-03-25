package jp.gamers_ranc.DTO.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import jp.gamers_ranc.Entity.user.User;
import jp.gamers_ranc.Entity.user.UserRole;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserSignupRequestDto {

    @NotBlank(message = "이메일은 필수 입력값입니다.")
    @Email(message = "이메일 형식이 올바르지 않습니다.")
    private String email;

    @NotBlank(message = "비밀번호는 필수 입력값입니다.")
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[!@#$%^&*()])[a-zA-Z\\d!@#$%^&*()]{8,}$", message = "비밀번호는 8자 이상, 영문자, 숫자, 특수문자를 포함해야 합니다.")
    private String password;

    @NotBlank(message = "비밀번호 확인은 필수 입력값입니다.")
    private String passwordConfirm;

    @NotBlank(message = "닉네임은 필수 입력값입니다.")
    @Size(min = 2, max = 20, message = "닉네임은 2자 이상 20자 이하여야 합니다.")
    private String nickname;

    @NotBlank(message = "전화번호는 필수 입력값입니다.")
    @Pattern(regexp = "^\\d{11}$", message = "전화번호는 11자리 숫자로 입력해주세요. (예: 01012345678)")
    private String phoneNumber;

    @Builder
    public UserSignupRequestDto(String email, String password, String passwordConfirm, String nickname,
            String phoneNumber) {
        this.email = email;
        this.password = password;
        this.passwordConfirm = passwordConfirm;
        this.nickname = nickname;
        this.phoneNumber = phoneNumber;
    }

    public User toEntity(String encodedPassword) {
        return User.builder()
                .email(email)
                .password(encodedPassword)
                .nickname(nickname)
                .phoneNumber(phoneNumber)
                .role(UserRole.USER)
                .build();
    }
}
