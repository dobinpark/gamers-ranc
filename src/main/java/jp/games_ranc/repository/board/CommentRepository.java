package jp.games_ranc.repository.board;

import jp.games_ranc.entity.board.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    Page<Comment> findByPostId(Long postId, Pageable pageable);
    Page<Comment> findByPostIdAndParentIsNull(Long postId, Pageable pageable);
}
