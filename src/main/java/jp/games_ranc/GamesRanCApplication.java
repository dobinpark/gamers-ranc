package jp.games_ranc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class GamesRanCApplication {
    public static void main(String[] args) {
        SpringApplication.run(GamesRanCApplication.class, args);
    }
}
