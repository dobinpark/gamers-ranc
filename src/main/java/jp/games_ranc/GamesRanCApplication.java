package jp.games_ranc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Arrays;

@EnableJpaAuditing
@SpringBootApplication
public class GamesRanCApplication {
    public static void main(String[] args) {
        String profile = System.getenv("SPRING_PROFILES_ACTIVE");
        if (profile == null) {
            // JAR 파일로 실행될 때 prod 프로필 적용
            if (Arrays.asList(args).contains("--spring.profiles.active=prod")) {
                System.setProperty("spring.profiles.active", "prod");
            }
        }
        SpringApplication.run(GamesRanCApplication.class, args);
    }
}
