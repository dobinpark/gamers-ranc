package jp.gamers_ranc.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jp.gamers_ranc.DTO.jwt.TokenDto;
import jp.gamers_ranc.DTO.user.UserLoginRequestDto;
import jp.gamers_ranc.DTO.user.UserResponseDto;
import jp.gamers_ranc.DTO.user.UserSignupRequestDto;
import jp.gamers_ranc.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Auth", description = "인증 API")
public class AuthController {

    private final UserService userService;


    @Operation(summary = "회원가입", description = "새로운 사용자를 등록합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "회원가입 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청")
    })
    @PostMapping("/signup")
    public ResponseEntity<UserResponseDto> signup(@Valid @RequestBody UserSignupRequestDto request) {
        // 비밀번호 확인 검증
        if (!request.getPassword().equals(request.getPasswordConfirm())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        UserResponseDto response = userService.signup(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    @Operation(summary = "로그인", description = "사용자 로그인 및 JWT 토큰을 발급합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "로그인 성공"),
            @ApiResponse(responseCode = "401", description = "인증 실패")
    })
    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@Valid @RequestBody UserLoginRequestDto request) {
        TokenDto tokenDto = userService.login(request);
        return ResponseEntity.ok(tokenDto);
    }
}
