package jp.games_ranc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import jp.games_ranc.config.jwt.JwtProperties;

@EnableJpaAuditing // created_at, updated_at 자동 업데이트
@SpringBootApplication
@EnableConfigurationProperties(JwtProperties.class)
public class GamesRanCApplication {
    public static void main(String[] args) {
        SpringApplication.run(GamesRanCApplication.class, args);
    }

}
