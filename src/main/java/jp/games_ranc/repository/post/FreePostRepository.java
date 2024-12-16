package jp.games_ranc.repository.post;

import jp.games_ranc.Entity.post.FreePost;
import jp.games_ranc.repository.PostRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FreePostRepository extends PostRepository<FreePost> {
}
