package jp.gamers_ranc.repository.post;

import jp.gamers_ranc.Entity.post.Comment;
import jp.gamers_ranc.Entity.post.Post;
import jp.gamers_ranc.Entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByPostOrderByCreatedAtDesc(Post post);
    Optional<Comment> findByIdAndAuthor(Long id, User author);
}
