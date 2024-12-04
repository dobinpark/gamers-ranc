package jp.games_ranc.service;

import jp.games_ranc.DTO.token.TokenResponse;
import jp.games_ranc.DTO.user.AddUserRequest;
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
        AddUserRequest request = new AddUserRequest();
        request.setEmail("test@test.com");
        request.setPassword("password");
        request.setNickname("tester");
        
        // when
        TokenResponse response = userService.signup(request);
        
        // then
        assertNotNull(response.getAccessToken());
        assertNotNull(response.getRefreshToken());
    }
} 