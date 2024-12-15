package jp.games_ranc.controller;

import jakarta.validation.Valid;
import jp.games_ranc.DTO.LoginRequest;
import jp.games_ranc.DTO.SignupRequest;
import jp.games_ranc.DTO.UserResponse;
import jp.games_ranc.DTO.UserUpdateRequest;
import jp.games_ranc.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<UserResponse> signup(@Valid @RequestBody SignupRequest request) {
        return ResponseEntity.ok(userService.signup(request));
    }

    @PostMapping("/login")
    public ResponseEntity<UserResponse> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(userService.login(request));
    }

    @GetMapping("/{email}")
    public ResponseEntity<UserResponse> getUser(@PathVariable String email) {
        return ResponseEntity.ok(userService.findByEmail(email));
    }

    @PutMapping("/{email}")
    public ResponseEntity<UserResponse> updateUser(
            @PathVariable String email,
            @Valid @RequestBody UserUpdateRequest request) {
        return ResponseEntity.ok(userService.updateUser(email, request));
    }

    @DeleteMapping("/{email}")
    public ResponseEntity<Void> deleteUser(@PathVariable String email) {
        userService.deleteUser(email);
        return ResponseEntity.noContent().build();
    }
}
