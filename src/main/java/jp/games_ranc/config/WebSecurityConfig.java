package jp.games_ranc.config;

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
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

        private final UserDetailService userService;

        // 스프링 시큐리티 기능 비활성화
        /*
         * 스프링 시큐리티의 모든 기능을 사용하지 않게 설정하는 코드
         * 일반적으로 정적 리소스(이미지, HTML 파일)에 설정
         */
        @Bean
        public WebSecurityCustomizer configure() {
                return (web) -> web.ignoring()
                                .requestMatchers(toH2Console())
                                .requestMatchers(new AntPathRequestMatcher("/static/**"));
        }

        // 특정 HTTP 요청에 대한 웹 기반 보안 구성
        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
                return http
                                .authorizeHttpRequests(auth -> auth
                                                .requestMatchers("/api/login", "/api/signup", "/login", "/signup",
                                                                "/user")
                                                .permitAll()
                                                .requestMatchers("/api/**").authenticated()
                                                .anyRequest().authenticated())
                                // anyRequest() : 위에서 설정한 url 이외의 요청에 대해 설정
                                // authenticated() : 별도의 인가는 필요하지 않지만 인증이 성공된 상태여야 접근 가능
                                .formLogin(formLogin -> formLogin
                                                .loginPage("/login") // 로그인 페이지 경로 설정
                                                .defaultSuccessUrl("/articles", true) // 로그인이 완료되었을 때 이동할 경로 설정
                                                .failureUrl("/login?error=true") // 로그인 실패시 처리
                                                .permitAll())
                                .logout(logout -> logout
                                                .logoutUrl("/api/logout")
                                                .logoutSuccessUrl("/login") // 로그아웃이 완료되었을 때 이동할 경로 설정
                                                .deleteCookies("JSESSIONID", "accessToken", "refreshToken") // 세션 쿠키 삭제
                                                .clearAuthentication(true) // 인증 정보 삭제
                                                .invalidateHttpSession(true) // 로그아웃 이후에 세션을 전체 삭제할지 여부 설정
                                )
                                .sessionManagement(session -> session
                                                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                                                .maximumSessions(1) // 통신 세션 제한
                                                .maxSessionsPreventsLogin(true) // 새 로그인 차단
                                )
                                .csrf(csrf -> csrf
                                                .ignoringRequestMatchers("/api/**")
                                                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
                                .build();
        }

        // 인증 관리자 관련 설정
        @Bean
        public AuthenticationManager authenticationManager(HttpSecurity http,
                        BCryptPasswordEncoder bCryptPasswordEncoder,
                        UserDetailService userDetailService) throws Exception {
                DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
                authProvider.setUserDetailsService(userService);
                authProvider.setPasswordEncoder(bCryptPasswordEncoder);
                return new ProviderManager(authProvider);
        }

        // 패스워드 인코더로 사용할 빈 등록
        @Bean
        public BCryptPasswordEncoder bCryptPasswordEncoder() {
                return new BCryptPasswordEncoder();
        }
}