package jp.games_ranc.repository.board;

import jp.games_ranc.entity.board.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    // 작성자에 따라 포스트 조회
    List<Post> findByAuthor(String author);

    // 제목으로 포스트 조회
    List<Post> findByTitleContaining(String title);

    // 특정 포스트 삭제
    void deleteById(Long id);

    // 특정 게시판 코드에 해당하는 포스트 조회
    List<Post> findByBoardCode(String boardCode);

    // 특정 게시판 코드와 ID에 해당하는 포스트 조회
    Post findByBoardCodeAndId(String boardCode, Long id);

    Page<Post> findByBoard_BoardCode(String boardCode, Pageable pageable);

    Optional<Post> findByIdAndBoard_BoardCode(Long id, String boardCode);

    Optional<Post> findByBoard_CodeAndId(String boardCode, Long id);
}
