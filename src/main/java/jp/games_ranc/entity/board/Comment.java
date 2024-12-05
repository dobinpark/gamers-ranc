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
public class Comment extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    private User author;

    @ManyToOne(fetch = FetchType.LAZY)
    private Comment parent; // 대댓글을 위한 부모 댓글 참조

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Builder
    public Comment(Post post, User author, Comment parent, String content) {
        this.post = post;
        this.author = author;
        this.parent = parent;
        this.content = content;
    }

    public void updateContent(String content) {
        this.content = content;
    }
}
