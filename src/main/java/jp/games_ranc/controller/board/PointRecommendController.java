package jp.games_ranc.controller.board;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jp.games_ranc.DTO.board.PointRecommendRequest;
import jp.games_ranc.DTO.board.PointRecommendResponse;
import jp.games_ranc.service.board.PointRecommendService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "PointRecommend", description = "포인트 추천 API")
@RestController
@RequestMapping("/api/posts/{postId}/point-recommends")
@RequiredArgsConstructor
public class PointRecommendController {

    private final PointRecommendService pointRecommendService;

    @Operation(summary = "포인트 추천", description = "게시글에 포인트를 사용하여 추천합니다.")
    @PostMapping
    public ResponseEntity<PointRecommendResponse> addPointRecommend(
            @PathVariable Long postId,
            @RequestBody PointRecommendRequest request) {
        return ResponseEntity.ok(
                pointRecommendService.addPointRecommend(postId, request.getUserId(),
                        request.getPointAmount()));
    }

    @Operation(summary = "포인트 추천 현황", description = "게시글의 포인트 추천 현황을 조회합니다.")
    @GetMapping
    public ResponseEntity<PointRecommendResponse> getPointRecommends(
            @PathVariable Long postId) {
        return ResponseEntity.ok(pointRecommendService.getPointRecommends(postId));
    }
}
