package jp.games_ranc.service;

import jp.games_ranc.DTO.token.TokenResponse;
import jp.games_ranc.DTO.user.SignUpRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class UserServiceTest {
    
    @Autowired
    private UserService userService;
    
    @Test
    void signupTest() {
        // given
        SignUpRequest request = new SignUpRequest();
        request.setEmail("test@test.com");
        request.setPassword("password");
        request.setNickname("tester");
        
        // when

        // then
    }
}
