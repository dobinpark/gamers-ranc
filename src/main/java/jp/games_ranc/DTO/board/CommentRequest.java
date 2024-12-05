package jp.games_ranc.DTO.board;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentRequest {

    private Long userId;

    private String content;

    private Long parentId;  // 대댓글인 경우 부모 댓글 ID
}
