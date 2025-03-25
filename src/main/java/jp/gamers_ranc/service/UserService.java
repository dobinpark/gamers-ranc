package jp.gamers_ranc.service;

import jp.gamers_ranc.DTO.jwt.TokenDto;
import jp.gamers_ranc.DTO.user.UserLoginRequestDto;
import jp.gamers_ranc.DTO.user.UserResponseDto;
import jp.gamers_ranc.DTO.user.UserSignupRequestDto;
import jp.gamers_ranc.DTO.user.UserUpdateRequest;
import jp.gamers_ranc.Entity.user.User;
import jp.gamers_ranc.config.jwt.JwtTokenProvider;
import jp.gamers_ranc.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;


    // 회원가입 (Create)
    @Transactional
    public UserResponseDto signup(UserSignupRequestDto request) {
        // 중복 검증
        validateSignup(request);

        // 새로운 유저 생성 및 저장
        User user = request.toEntity(passwordEncoder.encode(request.getPassword()));
        User savedUser = userRepository.save(user);

        return new UserResponseDto(savedUser);
    }


    // 로그인 및 JWT 토큰 발급
    public TokenDto login(UserLoginRequestDto request) {
        // 이메일로 유저 조회
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new BadCredentialsException("존재하지 않는 이메일입니다"));

        // 비밀번호 검증
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("비밀번호가 일치하지 않습니다");
        }

        // JWT 토큰 생성
        String token = jwtTokenProvider.createToken(user.getEmail(), user.getRole());

        return TokenDto.builder()
                .token(token)
                .email(user.getEmail())
                .build();
    }


    // 이메일로 유저 조회 (Read)
    public UserResponseDto findByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 이메일입니다"));
        return new UserResponseDto(user);
    }


    // 모든 유저 조회 (Read)
    public List<UserResponseDto> findAll() {
        return userRepository.findAll().stream()
                .map(UserResponseDto::new)
                .collect(Collectors.toList());
    }


    // 유저 정보 수정 (Update)
    @Transactional
    public UserResponseDto updateUser(String email, UserUpdateRequest request) {
        // 기존 유저 조회
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 이메일입니다"));

        // 닉네임 중복 검증
        if (request.getNickname() != null && !user.getNickname().equals(request.getNickname())) {
            validateNickname(request.getNickname());
        }

        // 유저 정보 업데이트
        user.update(
                request.getNickname(),
                request.getPassword() != null ? passwordEncoder.encode(request.getPassword()) : null,
                request.getPhoneNumber());

        // 변경된 유저 정보 저장
        User updatedUser = userRepository.save(user);
        return new UserResponseDto(updatedUser);
    }


    // 유저 삭제 (Delete)
    @Transactional
    public void deleteUser(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 이메일입니다"));
        userRepository.delete(user);
    }


    // 이메일 존재 여부 확인
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }


    // 닉네임 존재 여부 확인
    public boolean existsByNickname(String nickname) {
        return userRepository.existsByNickname(nickname);
    }

    private void validateSignup(UserSignupRequestDto request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("이미 존재하는 이메일입니다");
        }
        if (userRepository.existsByNickname(request.getNickname())) {
            throw new IllegalArgumentException("이미 존재하는 닉네임입니다");
        }
    }

    private void validateNickname(String nickname) {
        if (userRepository.existsByNickname(nickname)) {
            throw new IllegalArgumentException("이미 존재하는 닉네임입니다");
        }
    }


    // findUserByEmail 메서드 추가
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 이메일입니다"));
    }
}
