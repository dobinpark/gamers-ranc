package jp.games_ranc.service;

import jp.games_ranc.DTO.JoinDTO;
import jp.games_ranc.entity.User;
import jp.games_ranc.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JoinService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public void joinProcess(JoinDTO joinDTO) {

        String userName = joinDTO.getUserName();
        String password = joinDTO.getPassword();

        Boolean isExist = userRepository.existsByUsername(userName);

        if (isExist) {

            return;
        }

        User data = new User();

        data.setUsername(userName);
        data.setPassword(bCryptPasswordEncoder.encode(password));
        data.setRole("ROLE_ADMIN");

        userRepository.save(data);
    }
}
