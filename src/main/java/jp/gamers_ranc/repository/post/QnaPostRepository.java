package jp.gamers_ranc.repository.post;

import jp.gamers_ranc.Entity.post.QnaPost;
import jp.gamers_ranc.repository.PostRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QnaPostRepository extends PostRepository<QnaPost> {
}
