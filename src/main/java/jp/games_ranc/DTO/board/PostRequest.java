package jp.games_ranc.DTO.board;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostRequest {

    private String title;

    private String content;

    private String gameUrl; // 게임 소개 게시판용
}
