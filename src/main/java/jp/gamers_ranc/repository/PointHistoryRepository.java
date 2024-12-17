package jp.gamers_ranc.repository;

import jp.gamers_ranc.Entity.user.PointHistory;
import jp.gamers_ranc.Entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PointHistoryRepository extends JpaRepository<PointHistory, Long> {

    List<PointHistory> findByUserOrderByCreatedAtDesc(User user);
}
