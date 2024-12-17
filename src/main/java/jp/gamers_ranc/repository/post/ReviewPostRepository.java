package jp.gamers_ranc.repository.post;

import jp.gamers_ranc.Entity.post.ReviewPost;
import jp.gamers_ranc.repository.PostRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewPostRepository extends PostRepository<ReviewPost> {
}
