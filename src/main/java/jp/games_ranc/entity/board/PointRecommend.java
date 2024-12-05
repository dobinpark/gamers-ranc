package jp.games_ranc.entity.board;

import jakarta.persistence.*;
import jp.games_ranc.entity.BaseTimeEntity;
import jp.games_ranc.entity.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PointRecommend extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Post post; // 게시물

    @ManyToOne(fetch = FetchType.LAZY)
    private User user; // 추천한 사용자

    @Column(nullable = false)
    private int pointAmount; // 사용한 포인트 양

    @Builder
    public PointRecommend(Post post, User user, int pointAmount) {
        this.post = post;
        this.user = user;
        this.pointAmount = pointAmount;
    }
}
