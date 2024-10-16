package jp.games_ranc.controller;

import jp.games_ranc.DTO.LoginRequestDto;
import jp.games_ranc.DTO.SignupRequestDto;
import jp.games_ranc.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody SignupRequestDto requestDto) {
        userService.signup(requestDto);
        return ResponseEntity.ok("회원가입 성공");
    }

    @PostMapping("login")
    public ResponseEntity<String> login(@RequestBody LoginRequestDto requestDto) {
        return ResponseEntity.ok("로그인 성공!");
    }
}
