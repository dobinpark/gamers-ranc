package jp.gamers_ranc.service;

import jp.gamers_ranc.DTO.user.LoginRequest;
import jp.gamers_ranc.DTO.user.SignupRequest;
import jp.gamers_ranc.DTO.user.UserResponse;
import jp.gamers_ranc.DTO.user.UserUpdateRequest;
import jp.gamers_ranc.Entity.user.User;
import jp.gamers_ranc.repository.UserRepository;
import lombok.RequiredArgsConstructor;
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

    // 회원가입 (Create)
    @Transactional
    public UserResponse signup(SignupRequest request) {
        // 중복 검증
        validateSignup(request);

        // 새로운 유저 생성 및 저장
        User user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .nickname(request.getNickname())
                .build();

        User savedUser = userRepository.save(user);
        return UserResponse.from(savedUser);
    }

    // 로그인 (Read)
    public UserResponse login(LoginRequest request) {
        // 이메일로 유저 조회
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 이메일입니다"));

        // 비밀번호 검증
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다");
        }

        return UserResponse.from(user);
    }

    // 이메일로 유저 조회 (Read)
    public UserResponse findByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 이메일입니다"));
        return UserResponse.from(user);
    }

    // 모든 유저 조회 (Read)
    public List<UserResponse> findAll() {
        return userRepository.findAll().stream()
                .map(UserResponse::from)
                .collect(Collectors.toList());
    }

    // 유저 정보 수정 (Update)
    @Transactional
    public UserResponse updateUser(String email, UserUpdateRequest request) {
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
                request.getPassword() != null ?
                        passwordEncoder.encode(request.getPassword()) : null
        );

        // 변경된 유저 정보 저장
        User updatedUser = userRepository.save(user);
        return UserResponse.from(updatedUser);
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

    private void validateSignup(SignupRequest request) {
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
