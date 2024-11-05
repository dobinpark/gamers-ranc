package jp.games_ranc.repository;

import jp.games_ranc.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogRepository extends JpaRepository<Article, Long> {

}
