package jp.games_ranc.DTO.board;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class CommentResponse {

    private Long id;

    private String content;

    private Long authorId;

    private String authorName;

    private Long parentId;

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;
}
