package jp.games_ranc.DTO.board;

import jp.games_ranc.entity.board.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostResponse {

    private Long id;

    private String title;

    private String content;

    private String gameUrl;

    private int viewCount;

    private int likeCount;

    private int pointCount;
    
    public PostResponse(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.likeCount = post.getLikeCount();
        this.pointCount = post.getPointCount();
    }
}
