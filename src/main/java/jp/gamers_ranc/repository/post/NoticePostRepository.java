package jp.gamers_ranc.repository.post;

import jp.gamers_ranc.Entity.post.NoticePost;
import jp.gamers_ranc.repository.PostRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoticePostRepository extends PostRepository<NoticePost> {
}
