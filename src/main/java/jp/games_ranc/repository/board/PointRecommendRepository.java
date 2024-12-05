package jp.games_ranc.repository.board;

import jp.games_ranc.entity.board.PointRecommend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PointRecommendRepository extends JpaRepository<PointRecommend, Long> {

    List<PointRecommend> findByPostId(Long postId);

    @Query("SELECT SUM(pr.pointAmount) FROM PointRecommend pr WHERE pr.post.id = :postId")
    int sumPointAmountByPostId(Long postId);
}
