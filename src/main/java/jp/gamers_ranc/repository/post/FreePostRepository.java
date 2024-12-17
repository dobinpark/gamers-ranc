package jp.gamers_ranc.repository.post;

import jp.gamers_ranc.Entity.post.FreePost;
import jp.gamers_ranc.repository.PostRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FreePostRepository extends PostRepository<FreePost> {
}
