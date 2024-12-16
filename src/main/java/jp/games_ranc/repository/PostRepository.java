package jp.games_ranc.repository;

import jp.games_ranc.Entity.post.Post;
import jp.games_ranc.Entity.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository <T extends Post> extends JpaRepository<T, Long> {

    List<T> findAllByOrderByCreatedAtDesc();  // 페이징 없는 버전 유지
    Page<T> findAllByOrderByCreatedAtDesc(Pageable pageable);  // 페이징 버전 추가
    Optional<T> findByIdAndAuthor(Long id, User author);
    Page<T> findByTitleContainingOrContentContaining(
            String title,
            String content,
            Pageable pageable
    );
}
