package jp.games_ranc.service;

import jp.games_ranc.DTO.AddUserRequest;
import jp.games_ranc.DTO.CreateAccessTokenResponse;
import jp.games_ranc.config.jwt.TokenProvider;
import jp.games_ranc.entity.RefreshToken;
import jp.games_ranc.entity.User;
import jp.games_ranc.repository.RefreshTokenRepository;
import jp.games_ranc.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional
    public CreateAccessTokenResponse save(AddUserRequest dto) {
        // 유저 생성및 저장
        User user = User.builder()
                .email(dto.getEmail())
                .nickname(dto.getNickname())
                .password(passwordEncoder.encode(dto.getPassword()))
                .build();
        userRepository.save(user);

        // 엑세스 토큰 및 리프레시 토큰 생성
        String accessToken = tokenProvider.generateToken(user, Duration.ofHours(2)); // 엑세스 토큰이 2시간동안 유효
        String refreshToken = tokenProvider.generateToken(user, Duration.ofDays(7)); // 리프레시 토큰이 7일동안 유효

        // 리프레시 토큰 저장
        RefreshToken refreshTokenEntity = new RefreshToken(user.getId(), refreshToken);
        refreshTokenRepository.save(refreshTokenEntity);

        // 엑세스 토큰 반환
        return new CreateAccessTokenResponse(accessToken);
    }

    public User findById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Unexpected user"));
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Unexpected user"));
    }
}