package jp.games_ranc.entity.board;

import jakarta.persistence.*;
import jp.games_ranc.entity.BaseTimeEntity;
import jp.games_ranc.entity.User;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Board board; // 게시판

    @ManyToOne(fetch = FetchType.LAZY)
    private User author; // 작성자

    @Column(nullable = false)
    private String title; // 제목

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content; // 내용

    @Column
    private String gameUrl; // 게임 링크 (게임 소개 게시판용)

    @Column(nullable = false)
    private int viewCount; // 조회수

    @Column(nullable = false)
    private int likeCount; // 일반 추천수

    @Column(nullable = false)
    private int pointCount; // 포인트 추천수

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<PostLike> likes = new ArrayList<>();

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<PointRecommend> pointRecommends = new ArrayList<>();

    @Builder
    public Post(Board board, User author, String title, String content,
                String gameUrl) {
        this.board = board;
        this.author = author;
        this.title = title;
        this.content = content;
        this.gameUrl = gameUrl;
        this.viewCount = 0;
        this.likeCount = 0;
        this.pointCount = 0;
    }

    // 좋아요 수 증가 메서드
    public void incrementLikeCount() {
        this.likeCount++;
    }

    // 좋아요 수 감소 메서드
    public void decrementLikeCount() {
        if (this.likeCount > 0) {
            this.likeCount--;
        }
    }

    public void addPointCount(int points) {
        this.pointCount += points;
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
