package jp.games_ranc.repository.board;

import jp.games_ranc.entity.board.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
     // Pageable을 사용하여 페이징 처리
     Page<Post> findByBoard_BoardCode(String boardCode, Pageable pageable);
    
     // ID와 게시판 코드로 게시글 찾기
     Optional<Post> findByIdAndBoard_BoardCode(Long id, String boardCode);
}
