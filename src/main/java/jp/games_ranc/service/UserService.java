package jp.games_ranc.service;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jp.games_ranc.DTO.token.TokenResponse;
import jp.games_ranc.DTO.user.LoginRequest;
import jp.games_ranc.DTO.user.SignUpRequest;
import jp.games_ranc.DTO.user.UserUpdateRequest;
import jp.games_ranc.config.jwt.TokenProvider;
import jp.games_ranc.entity.RefreshToken;
import jp.games_ranc.entity.User;
import jp.games_ranc.exception.CustomException;
import jp.games_ranc.exception.ErrorCode;
import jp.games_ranc.repository.RefreshTokenRepository;
import jp.games_ranc.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional
    public void signup(@Valid SignUpRequest request) {
        if (isEmailDuplicate(request.getEmail())) {
            throw new IllegalArgumentException("이미 가입된 이메일입니다.");
        }
        saveUser(request);
    }

    @Transactional
    public TokenResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        validatePassword(request.getPassword(), user.getPassword());
        return createTokenResponse(user);
    }

    public boolean isEmailDuplicate(String email) {
        return userRepository.existsByEmail(email);
    }

    private User saveUser(SignUpRequest request) {
        String encodedPassword = passwordEncoder.encode(request.getPassword());
        return userRepository.save(User.builder()
                .email(request.getEmail())
                .password(encodedPassword)
                .nickname(request.getNickname())
                .build());
    }

    private TokenResponse createTokenResponse(User user) {
        String accessToken = tokenProvider.generateToken(user, Duration.ofHours(2));
        String refreshToken = tokenProvider.generateToken(user, Duration.ofDays(7));

        RefreshToken refreshTokenEntity = new RefreshToken(user.getId(), refreshToken);
        refreshTokenRepository.save(refreshTokenEntity);

        return new TokenResponse(accessToken, refreshToken);
    }

    public User findById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Unexpected user"));
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Unexpected user"));
    }

    private void addTokenToCookie(HttpServletResponse response, String name, String value, Duration maxAge) {
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        cookie.setMaxAge((int) maxAge.toSeconds());
        cookie.setHttpOnly(true); // XSS 공격 방지
        cookie.setSecure(true); // HTTPS에서만 전송
        response.addCookie(cookie);
    }

    private void validatePassword(String rawPassword, String encodedPassword) {
        if (!passwordEncoder.matches(rawPassword, encodedPassword)) {
            throw new CustomException(ErrorCode.INVALID_PASSWORD);
        }
    }

    @Transactional
public void updateUser(String email, UserUpdateRequest request) {
        User user = findByEmail(email);
        user.updateProfile(request.getNickname());  // 필요한 필드 업데이트
    }

    @Transactional
    public void deleteUser(String email) {
        User user = findByEmail(email);
        userRepository.delete(user);
    }
}