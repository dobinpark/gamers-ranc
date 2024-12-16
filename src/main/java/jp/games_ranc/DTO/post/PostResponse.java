package jp.games_ranc.DTO.post;

import jp.games_ranc.Entity.post.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class PostResponse {

    private Long id;
    private String title;
    private String content;
    private String authorEmail;
    private String authorNickname;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static PostResponse from(Post post) {
        return new PostResponse(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getAuthor().getEmail(),
                post.getAuthor().getNickname(),
                post.getCreatedAt(),
                post.getUpdatedAt()
        );
    }
}
