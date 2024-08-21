package jp.games_ranc.service;

import jp.games_ranc.entity.User;
import jp.games_ranc.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User addUser(User user) {
        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User findUserById(Long id) {
        return userRepository.findById(String.valueOf(id)).orElseThrow(
                () -> new UserNotFoundException("User with id " + id + " not found!"));
    }

    public void delete(Long id) {
        userRepository.deleteById(String.valueOf(id));
    }
}
