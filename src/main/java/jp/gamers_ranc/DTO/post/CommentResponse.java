package jp.gamers_ranc.DTO.post;

import jp.gamers_ranc.Entity.post.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class CommentResponse {

    private Long id;
    private String content;
    private String authorEmail;
    private String authorNickname;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static CommentResponse from(Comment comment) {
        return new CommentResponse(
                comment.getId(),
                comment.getContent(),
                comment.getAuthor().getEmail(),
                comment.getAuthor().getNickname(),
                comment.getCreatedAt(),
                comment.getUpdatedAt()
        );
    }
}
