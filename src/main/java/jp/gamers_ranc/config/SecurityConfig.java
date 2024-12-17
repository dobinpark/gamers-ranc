package jp.gamers_ranc.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // CSRF 비활성화
                .csrf(csrf -> {csrf.disable();
                    // H2 콘솔용 CSRF 비활성화
                    csrf.ignoringRequestMatchers("/h2-console/**");
                })

                // 기본 보안 헤더 비활성화
                .headers(headers -> {
                    headers.disable();
                    // X-Frame-Options 비활성화 (H2 콘솔용)
                    headers.frameOptions(frame -> frame.disable());
                })

                // 요청에 대한 권한 설정
                .authorizeHttpRequests(auth -> auth
                        // H2 콘솔 접근 허용
                        .requestMatchers("/h2-console/**").permitAll()
                        // Swagger UI 접근 허용
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
                        // API 엔드포인트 접근 허용
                        .requestMatchers("/api/users/**").permitAll()
                        // 그 외 모든 요청은 인증 필요
                        .anyRequest().authenticated()
                )

                // 세션 관리 비활성화 (JWT 사용 예정이므로)
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                );

        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
