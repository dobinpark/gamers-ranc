package jp.games_ranc.service;

import jp.games_ranc.DTO.LoginRequest;
import jp.games_ranc.DTO.SignupRequest;
import jp.games_ranc.DTO.UserResponse;
import jp.games_ranc.DTO.UserUpdateRequest;
import jp.games_ranc.Entity.User;
import jp.games_ranc.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Transactional
    public UserResponse signup(SignupRequest request) {
        validateSignup(request);

        User user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .nickname(request.getNickname())
                .build();

        return UserResponse.from(userRepository.save(user));
    }

    public UserResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 이메일입니다"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다");
        }

        return UserResponse.from(user);
    }

    public UserResponse findByEmail(String email) {
        return UserResponse.from(getUserByEmail(email));
    }

    @Transactional
    public UserResponse updateUser(String email, UserUpdateRequest request) {
        User user = getUserByEmail(email);

        if (request.getNickname() != null) {
            validateNickname(request.getNickname());
        }

        user.update(
                request.getNickname(),
                request.getPassword() != null ?
                        passwordEncoder.encode(request.getPassword()) : null
        );

        return UserResponse.from(user);
    }

    @Transactional
    public void deleteUser(String email) {
        User user = getUserByEmail(email);
        userRepository.delete(user);
    }

    private User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 이메일입니다"));
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
}
