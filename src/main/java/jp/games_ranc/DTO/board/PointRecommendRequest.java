package jp.games_ranc.DTO.board;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PointRecommendRequest {

    private Long userId;

    private int pointAmount;
}
