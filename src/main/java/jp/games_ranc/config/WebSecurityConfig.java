package jp.games_ranc.config;

import jp.games_ranc.config.jwt.TokenProvider;
import jp.games_ranc.service.UserDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

        private final UserDetailService userDetailService;
        private final TokenProvider tokenProvider;

        // 스프링 시큐리티 기능 비활성화
        /*
         * 스프링 시큐리티의 모든 기능을 사용하지 않게 설정하는 코드
         * 일반적으로 정적 리소스(이미지, HTML 파일)에 설정
         */
        @Bean
        public WebSecurityCustomizer configure() {
                return (web) -> web.ignoring()
                                .requestMatchers(toH2Console())
                                .requestMatchers("/css/**", "/js/**", "/images/**")
                                .requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/swagger-ui.html");
        }

        // 특정 HTTP 요청에 대한 웹 기반 보안 구성
        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
                return http
                                .authorizeHttpRequests(auth -> auth
                                                .requestMatchers(
                                                                "/",
                                                                "/login",
                                                                "/signup",        // 회원가입 페이지
                                                                "/api/users/**",  // 회원가입 API
                                                                "/css/**",
                                                                "/js/**",
                                                                "/images/**",
                                                                "/h2-console/**",
                                                                "/swagger-ui/**",          // Swagger UI 허용
                                                                "/v3/api-docs/**",         // Swagger API docs 허용
                                                                "/swagger-ui.html"         // Swagger UI HTML 허용
                                                            ).permitAll()
                                                .anyRequest().authenticated())
                                .addFilterBefore(tokenAuthenticationFilter(),
                                                UsernamePasswordAuthenticationFilter.class) // JWT 필터 추가
                                .formLogin(formLogin -> formLogin
                                                .loginPage("/login") // 로그인 페이지 경로 설정
                                                .defaultSuccessUrl("/blog/articles")  // 로그인 성공 시 블로그 목록으로
                                                .failureUrl("/login?error=true") // 로그인 실패시 처리
                                                .permitAll())
                                .logout(logout -> logout
                                                .logoutSuccessUrl("/login") // 로그아웃이 완료되었을 때 이동할 경로 설정
                                                .invalidateHttpSession(true) // 로그아웃 이후에 세션을 전체 삭제할지 여부 설정
                                                .deleteCookies("JSESSIONID", "accessToken", "refreshToken") // 세션 쿠키 삭제
                                )
                                .sessionManagement(session -> session
                                                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                                                .maximumSessions(1) // 통신 세션 제한
                                                .maxSessionsPreventsLogin(true) // 새 로그인 차단
                                )
                                .csrf(csrf -> csrf
                                                .ignoringRequestMatchers("/api/**", "/h2-console/**"))
                                .headers(headers -> headers
                                                .frameOptions(frameOptions -> frameOptions.sameOrigin())
                                                .xssProtection(xss -> xss.disable())
                                                .contentSecurityPolicy(csp -> csp
                                                                .policyDirectives("default-src 'self'")))
                                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                                .build();
        }

        @Bean
        public CorsConfigurationSource corsConfigurationSource() {
                CorsConfiguration configuration = new CorsConfiguration();
                configuration.setAllowedOrigins(Arrays.asList("http://localhost:8080")); // 허용할 도메인
                configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
                configuration.setAllowedHeaders(Arrays.asList("*"));
                configuration.setAllowCredentials(true);

                UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
                source.registerCorsConfiguration("/**", configuration);
                return source;
        }

        // 인증 관리자 관련 설정
        @Bean
        public AuthenticationManager authenticationManager(
                        BCryptPasswordEncoder bCryptPasswordEncoder) {
                DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
                authProvider.setUserDetailsService(userDetailService);
                authProvider.setPasswordEncoder(bCryptPasswordEncoder);
                return new ProviderManager(authProvider);
        }

        // 패스워드 인코더로 사용할 빈 등록
        @Bean
        public BCryptPasswordEncoder bCryptPasswordEncoder() {
                return new BCryptPasswordEncoder();
        }

        // TokenAuthenticationFilter를 빈으로 등록
        @Bean
        public TokenAuthenticationFilter tokenAuthenticationFilter() {
                return new TokenAuthenticationFilter(tokenProvider);
        }
}