package jp.games_ranc.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import com.fasterxml.jackson.databind.ObjectMapper;
import jp.games_ranc.DTO.user.AddUserRequest;

@SpringBootTest
@AutoConfigureMockMvc
class UserApiControllerTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @Test
    void signupTest() throws Exception {
        // given
        AddUserRequest request = new AddUserRequest();
        request.setEmail("test@test.com");
        request.setPassword("password123");
        request.setNickname("tester");
        
        // when & then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.header().exists(HttpHeaders.AUTHORIZATION))
                .andExpect(MockMvcResultMatchers.jsonPath("$.accessToken").exists());
    }
} 