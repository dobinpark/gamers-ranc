package jp.games_ranc.service;

import jp.games_ranc.DTO.LoginRequestDto;
import jp.games_ranc.DTO.SignupRequestDto;
import jp.games_ranc.entity.User;
import jp.games_ranc.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public boolean login(LoginRequestDto loginRequestDto) {
        Optional<User> optionalUser = userRepository.findByUsername(loginRequestDto.getUsername());

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            return passwordEncoder.matches(loginRequestDto.getPassword(), user.getPassword());
        }
        return false;
    }

    public void signup(SignupRequestDto requestDto) {
        User user = User.builder()
                .username(requestDto.getUsername())
                .password(passwordEncoder.encode(requestDto.getPassword()))
                .secondaryPassword(passwordEncoder.encode(requestDto.getSecondaryPassword()))
                .nickname(requestDto.getNickname())
                .phoneNumber(requestDto.getPhoneNumber())
                .build();

        userRepository.save(user);
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User updateUser(Long id, LoginRequestDto signupRequestDto) {
        User user = getUserById(id);
        user.setUsername(signupRequestDto.getUsername());
        user.setPassword(passwordEncoder.encode(signupRequestDto.getPassword()));
        user.setSecondaryPassword(passwordEncoder.encode(signupRequestDto.getSecondaryPassword()));
        user.setNickname(signupRequestDto.getNickname());
        user.setPhoneNumber(signupRequestDto.getPhoneNumber());

        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        User user = getUserById(id);
        userRepository.delete(user);
    }
}
