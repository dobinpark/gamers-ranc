package jp.games_ranc.config.jwt;

import io.jsonwebtoken.*;
import jp.games_ranc.entity.User;
import lombok.RequiredArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Collections;
import java.util.Date;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class TokenProvider {

    private static final Logger logger = LoggerFactory.getLogger(TokenProvider.class);
    private static final String AUTHORITIES_KEY = "auth";
    private static final String ID_KEY = "id";

    private final JwtProperties jwtProperties;

    public String generateToken(User user, Duration expiredAt) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
        Date now = new Date();

        return makeToken(new Date(now.getTime() + expiredAt.toMillis()), user);
    }

    // JWT 토큰 생성 메서드
    private String makeToken(Date expiry, User user) {
        Date now = new Date();

        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE) // 헤더 Typ: JWT
                // 내용 iss : ff09560810@gmail.com(properties 파일에서 설정한 값)
                .setIssuer(jwtProperties.getIssuer())
                .setIssuedAt(now) // 내용 iat : 현재 시간
                .setExpiration(expiry) // 내용 exp : expiry 멤버 변수값
                .setSubject(user.getEmail()) // 내용 sub : 유저의 이메일
                .claim("id", user.getId()) // 클레임 id : 유자 ID
                .claim(AUTHORITIES_KEY, "ROLE_USER")
                // 서명 : 비밀값과 함께 해시값을 HS256 방식으로 암호화
                .signWith(SignatureAlgorithm.HS256, jwtProperties.getSecretKey())
                .compact();
    }

    // JWT 토큰 유효성 검증 메서드
    public boolean validToken(String token) {
        if (token == null || token.isEmpty()) {
            return false;
        }

        try {
            Jwts.parser()
                    .setSigningKey(jwtProperties.getSecretKey()) // 비밀값으로 복호화
                    .parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException e) {
            logger.warn("만료된 JWT 토큰입니다: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.warn("지원되지 않는 JWT 토큰입니다: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            logger.warn("잘못된 형식의 JWT 토큰입니다: {}", e.getMessage());
        } catch (SignatureException e) {
            logger.warn("유효하지 않은 JWT 서명입니다: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.warn("JWT 토큰이 잘못되었습니다: {}", e.getMessage());
        }
        return false;
    }

    // 토큰 기반으로 인증 정보를 가져오는 메서드
    public Authentication getAuthentication(String token) {
        Claims claims = getClaims(token);
        Set<SimpleGrantedAuthority> authorities = Collections
                .singleton(new SimpleGrantedAuthority(claims.get(AUTHORITIES_KEY, String.class)));

        return new UsernamePasswordAuthenticationToken(new org.springframework.security.core.userdetails.User(
                claims.getSubject(), "", authorities), token, authorities);
    }

    // 토큰 기반으로 유저 ID를 가져오는 메서드
    public Long getUserId(String token) {
        Claims claims = getClaims(token);
        Long userId = claims.get(ID_KEY, Long.class);
        if (userId == null) {
            throw new JwtException("토큰에서 사용자 ID를 찾을 수 없습니다.");
        }

        return userId;
    }

    private Claims getClaims(String token) {
        try {
            return Jwts.parser() // 클레임 조회
                    .setSigningKey(jwtProperties.getSecretKey())
                    .parseClaimsJws(token)
                    .getBody();
        } catch (JwtException e) {
            logger.error("토큰 처리중 오류가 발생했습니다: {}", e.getMessage());
            throw e;
        }
    }
}
