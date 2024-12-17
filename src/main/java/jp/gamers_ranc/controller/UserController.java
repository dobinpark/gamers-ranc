package jp.gamers_ranc.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jp.gamers_ranc.DTO.user.LoginRequest;
import jp.gamers_ranc.DTO.user.SignupRequest;
import jp.gamers_ranc.DTO.user.UserResponse;
import jp.gamers_ranc.DTO.user.UserUpdateRequest;
import jp.gamers_ranc.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Tag(name = "User", description = "사용자 API")
public class UserController {

    private final UserService userService;

    // 회원가입
    @Operation(summary = "회원가입", description = "새로운 사용자를 등록합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "회원가입 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청")
    })
    @PostMapping("/signup")
    public ResponseEntity<UserResponse> signup(@Valid @RequestBody SignupRequest request) {
        UserResponse response = userService.signup(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // 로그인
    @Operation(summary = "로그인", description = "사용자 로그인을 처리합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "로그인 성공"),
            @ApiResponse(responseCode = "401", description = "인증 실패")
    })
    @PostMapping("/login")
    public ResponseEntity<UserResponse> login(@Valid @RequestBody LoginRequest request) {
        UserResponse response = userService.login(request);
        return ResponseEntity.ok(response);
    }

    // 모든 유저 조회
    @Operation(summary = "전체 사용자 조회", description = "모든 사용자 정보를 조회합니다.")
    @ApiResponse(responseCode = "200", description = "조회 성공")
    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        List<UserResponse> users = userService.findAll();
        return ResponseEntity.ok(users);
    }

    // 특정 유저 조회
    @Operation(summary = "회원 정보 조회", description = "이메일로 회원 정보를 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(responseCode = "404", description = "사용자 없음")
    })
    @GetMapping("/{email}")
    public ResponseEntity<UserResponse> getUser(@PathVariable String email) {
        UserResponse response = userService.findByEmail(email);
        return ResponseEntity.ok(response);
    }

    // 유저 정보 수정
    @Operation(summary = "회원 정보 수정", description = "회원 정보를 수정합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "수정 성공"),
            @ApiResponse(responseCode = "404", description = "사용자 없음")
    })
    @PutMapping("/{email}")
    public ResponseEntity<UserResponse> updateUser(
            @PathVariable String email,
            @Valid @RequestBody UserUpdateRequest request) {
        UserResponse response = userService.updateUser(email, request);
        return ResponseEntity.ok(response);
    }

    // 유저 삭제
    @Operation(summary = "회원 탈퇴", description = "회원 정보를 삭제합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "삭제 성공"),
            @ApiResponse(responseCode = "404", description = "사용자 없음")
    })
    @DeleteMapping("/{email}")
    public ResponseEntity<Void> deleteUser(@PathVariable String email) {
        userService.deleteUser(email);
        return ResponseEntity.noContent().build();
    }

    // 이메일 중복 확인
    @Operation(summary = "이메일 중복 확인", description = "회원가입 시 이메일 중복을 확인합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "사용 가능한 이메일"),
            @ApiResponse(responseCode = "409", description = "중복된 이메일")
    })
    @GetMapping("/exists/email/{email}")
    public ResponseEntity<Boolean> checkEmailExists(@PathVariable String email) {
        boolean exists = userService.existsByEmail(email);
        return ResponseEntity.ok(exists);
    }

    // 닉네임 중복 확인
    @Operation(summary = "닉네임 중복 확인", description = "회원가입 시 닉네임 중복을 확인합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "사용 가능한 닉네임"),
            @ApiResponse(responseCode = "409", description = "중복된 닉네임")
    })
    @GetMapping("/exists/nickname/{nickname}")
    public ResponseEntity<Boolean> checkNicknameExists(@PathVariable String nickname) {
        boolean exists = userService.existsByNickname(nickname);
        return ResponseEntity.ok(exists);
    }
}