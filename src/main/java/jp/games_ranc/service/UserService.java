package jp.games_ranc.service;

import jp.games_ranc.DTO.UserDto;
import jp.games_ranc.entity.User;
import jp.games_ranc.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User login(String email, String password) {
        User user = userRepository.findByEmail(email)

                .orElseThrow(() -> new RuntimeException("User not found"));
        if (passwordEncoder.matches(password, user.getPassword())) {
            return user;
        }

        throw new RuntimeException("Invalid credentials");
    }

    public void signup(UserDto userDto) {
        User user = User.builder()
                .email(userDto.getEmail())
                .nickname(userDto.getNickname())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .secondaryPassword(passwordEncoder.encode(userDto.getSecondaryPassword()))
                .phoneNumber(userDto.getPhoneNumber())
                .createdAt(userDto.getCreatedAt())
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

    public User updateUser(Long id, UserDto userDto) {
        User user = getUserById(id);
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setSecondaryPassword(passwordEncoder.encode(userDto.getSecondaryPassword()));
        user.setNickname(userDto.getNickname());
        user.setPhoneNumber(userDto.getPhoneNumber());
        user.setUpdatedAt(userDto.getUpdatedAt());

        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        User user = getUserById(id);
        userRepository.delete(user);
    }
}