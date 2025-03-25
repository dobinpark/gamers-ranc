package jp.gamers_ranc.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jp.gamers_ranc.DTO.user.UserResponseDto;
import jp.gamers_ranc.DTO.user.UserUpdateRequest;
import jp.gamers_ranc.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Tag(name = "User", description = "사용자 API")
public class UserController {

    private final UserService userService;


    // 인증된 사용자 정보 조회
    @Operation(summary = "내 정보 조회", description = "인증된 사용자의 정보를 조회합니다.")
    @ApiResponse(responseCode = "200", description = "조회 성공")
    @GetMapping("/me")
    public ResponseEntity<UserResponseDto> getMyInfo(@AuthenticationPrincipal UserDetails userDetails) {
        UserResponseDto response = userService.findByEmail(userDetails.getUsername());
        return ResponseEntity.ok(response);
    }


    // 모든 유저 조회 (관리자용)
    @Operation(summary = "전체 사용자 조회", description = "모든 사용자 정보를 조회합니다. (관리자용)")
    @ApiResponse(responseCode = "200", description = "조회 성공")
    @GetMapping
    public ResponseEntity<List<UserResponseDto>> getAllUsers() {
        List<UserResponseDto> users = userService.findAll();
        return ResponseEntity.ok(users);
    }


    // 특정 유저 조회
    @Operation(summary = "회원 정보 조회", description = "이메일로 회원 정보를 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(responseCode = "404", description = "사용자 없음")
    })
    @GetMapping("/{email}")
    public ResponseEntity<UserResponseDto> getUser(@PathVariable String email) {
        UserResponseDto response = userService.findByEmail(email);
        return ResponseEntity.ok(response);
    }


    // 유저 정보 수정
    @Operation(summary = "내 정보 수정", description = "로그인한 사용자의 정보를 수정합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "수정 성공"),
            @ApiResponse(responseCode = "401", description = "인증 실패")
    })
    @PutMapping("/me")
    public ResponseEntity<UserResponseDto> updateMyInfo(
            @AuthenticationPrincipal UserDetails userDetails,
            @Valid @RequestBody UserUpdateRequest request) {
        UserResponseDto response = userService.updateUser(userDetails.getUsername(), request);
        return ResponseEntity.ok(response);
    }


    // 유저 삭제 (본인 계정)
    @Operation(summary = "회원 탈퇴", description = "로그인한 사용자의 계정을 삭제합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "삭제 성공"),
            @ApiResponse(responseCode = "401", description = "인증 실패")
    })
    @DeleteMapping("/me")
    public ResponseEntity<Void> deleteMyAccount(@AuthenticationPrincipal UserDetails userDetails) {
        userService.deleteUser(userDetails.getUsername());
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
