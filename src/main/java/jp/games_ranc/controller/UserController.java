package jp.games_ranc.controller;

import jakarta.validation.Valid;
import jp.games_ranc.DTO.user.LoginRequest;
import jp.games_ranc.DTO.user.SignupRequest;
import jp.games_ranc.DTO.user.UserResponse;
import jp.games_ranc.DTO.user.UserUpdateRequest;
import jp.games_ranc.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // 회원가입
    @PostMapping("/signup")
    public ResponseEntity<UserResponse> signup(@Valid @RequestBody SignupRequest request) {
        UserResponse response = userService.signup(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<UserResponse> login(@Valid @RequestBody LoginRequest request) {
        UserResponse response = userService.login(request);
        return ResponseEntity.ok(response);
    }

    // 모든 유저 조회
    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        List<UserResponse> users = userService.findAll();
        return ResponseEntity.ok(users);
    }

    // 특정 유저 조회
    @GetMapping("/{email}")
    public ResponseEntity<UserResponse> getUser(@PathVariable String email) {
        UserResponse response = userService.findByEmail(email);
        return ResponseEntity.ok(response);
    }

    // 유저 정보 수정
    @PutMapping("/{email}")
    public ResponseEntity<UserResponse> updateUser(
            @PathVariable String email,
            @Valid @RequestBody UserUpdateRequest request) {
        UserResponse response = userService.updateUser(email, request);
        return ResponseEntity.ok(response);
    }

    // 유저 삭제
    @DeleteMapping("/{email}")
    public ResponseEntity<Void> deleteUser(@PathVariable String email) {
        userService.deleteUser(email);
        return ResponseEntity.noContent().build();
    }

    // 이메일 중복 확인
    @GetMapping("/exists/email/{email}")
    public ResponseEntity<Boolean> checkEmailExists(@PathVariable String email) {
        boolean exists = userService.existsByEmail(email);
        return ResponseEntity.ok(exists);
    }

    // 닉네임 중복 확인
    @GetMapping("/exists/nickname/{nickname}")
    public ResponseEntity<Boolean> checkNicknameExists(@PathVariable String nickname) {
        boolean exists = userService.existsByNickname(nickname);
        return ResponseEntity.ok(exists);
    }
}