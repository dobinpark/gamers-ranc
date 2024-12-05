package jp.games_ranc.config.jwt;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Base64;

@Setter
@Getter
@Component
@ConfigurationProperties("jwt")
public class JwtProperties {

    private String issuer;
    private String secret;
    private String header;
    private Long tokenValidityInSeconds;

    // Base64로 인코딩된 시크릿 키를 반환
    public String getSecretKey() {
        return Base64.getEncoder().encodeToString(this.secret.getBytes());
    }
}
