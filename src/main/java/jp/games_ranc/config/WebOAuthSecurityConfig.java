/*
 * package jp.games_ranc.config;
 * 
 * import jp.games_ranc.config.jwt.TokenProvider;
 * import jp.games_ranc.config.oauth2.
 * OAuth2AuthorizationRequestBasedOnCookieRepository;
 * import jp.games_ranc.config.oauth2.OAuth2SuccessHandler;
 * import jp.games_ranc.config.oauth2.OAuth2UserCustomService;
 * import jp.games_ranc.repository.RefreshTokenRepository;
 * import jp.games_ranc.service.UserService;
 * import lombok.RequiredArgsConstructor;
 * import org.springframework.context.annotation.Bean;
 * import org.springframework.context.annotation.Configuration;
 * import org.springframework.http.HttpStatus;
 * import
 * org.springframework.security.config.annotation.web.builders.HttpSecurity;
 * import org.springframework.security.config.annotation.web.configuration.
 * WebSecurityCustomizer;
 * import org.springframework.security.config.http.SessionCreationPolicy;
 * import org.springframework.security.config.annotation.web.configurers.
 * AbstractHttpConfigurer;
 * import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
 * import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
 * import org.springframework.security.web.SecurityFilterChain;
 * import org.springframework.security.web.authentication.HttpStatusEntryPoint;
 * import org.springframework.security.web.authentication.
 * UsernamePasswordAuthenticationFilter;
 * 
 * import static
 * org.springframework.boot.autoconfigure.security.servlet.PathRequest.
 * toH2Console;
 * 
 * @RequiredArgsConstructor
 * 
 * @Configuration
 * public class WebOAuthSecurityConfig {
 * 
 * private final OAuth2UserCustomService oAuth2UserCustomService;
 * private final TokenProvider tokenProvider;
 * private final RefreshTokenRepository refreshTokenRepository;
 * private final UserService userService;
 * 
 * @Bean
 * public WebSecurityCustomizer configure() { // 스프링 시큐리티 기능 비활성화
 * return (web) -> web.ignoring()
 * .requestMatchers(toH2Console())
 * .requestMatchers(
 * new AntPathRequestMatcher("/img/**"),
 * new AntPathRequestMatcher("/css/**"),
 * new AntPathRequestMatcher("/js/**")
 * );
 * }
 * 
 * @Bean
 * public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
 * // 토큰 방식으로 인증을 하기 때문에 기존에 사용하던 폼 로그인, 세션 비활성화
 * return http
 * .csrf(AbstractHttpConfigurer::disable)
 * .httpBasic(AbstractHttpConfigurer::disable)
 * .formLogin(AbstractHttpConfigurer::disable)
 * .logout(AbstractHttpConfigurer::disable)
 * .sessionManagement(management ->
 * management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
 * // 헤더를 확인할 커스텀 필터 추가
 * .addFilterBefore(tokenAuthenticationFilter(),
 * UsernamePasswordAuthenticationFilter.class)
 * // 토큰 재발급 URL은 인증없이 접근 가능하도록 설정, 나머지 API URL은 인증 필요
 * .authorizeRequests(auth -> auth
 * .requestMatchers(new AntPathRequestMatcher("/api/token")).permitAll()
 * .requestMatchers(new AntPathRequestMatcher("/api/**")).authenticated()
 * .anyRequest().permitAll())
 * .oauth2Login(oauth2 -> oauth2
 * .loginPage("/login")
 * // Authorization 요청과 관련된 상태 저장
 * .authorizationEndpoint(authorizationEndpoint ->
 * authorizationEndpoint.authorizationRequestRepository(
 * oAuth2AuthorizationRequestBasedOnCookieRepository()))
 * .userInfoEndpoint(userInfoEndpoint ->
 * userInfoEndpoint.userService(oAuth2UserCustomService))
 * // 인증 성공시 실행할 핸들러
 * .successHandler(oAuth2SuccessHandler())
 * )
 * // /api로 시작하는 URL인 경우 401 상태 코드를 반환하도록 예외 처리
 * .exceptionHandling(exceptionHandling -> exceptionHandling
 * .defaultAuthenticationEntryPointFor(
 * new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED),
 * new AntPathRequestMatcher("/api/**")
 * ))
 * .build();
 * }
 * 
 * @Bean
 * public OAuth2SuccessHandler oAuth2SuccessHandler() {
 * return new OAuth2SuccessHandler(tokenProvider,
 * refreshTokenRepository,
 * oAuth2AuthorizationRequestBasedOnCookieRepository(),
 * userService
 * );
 * }
 * 
 * @Bean
 * public TokenAuthenticationFilter tokenAuthenticationFilter() {
 * return new TokenAuthenticationFilter(tokenProvider);
 * }
 * 
 * @Bean
 * public OAuth2AuthorizationRequestBasedOnCookieRepository
 * oAuth2AuthorizationRequestBasedOnCookieRepository() {
 * return new OAuth2AuthorizationRequestBasedOnCookieRepository();
 * }
 * 
 * @Bean
 * public BCryptPasswordEncoder bCryptPasswordEncoder() {
 * return new BCryptPasswordEncoder();
 * }
 * }
 */
