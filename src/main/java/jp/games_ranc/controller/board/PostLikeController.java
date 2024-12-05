package jp.games_ranc.controller.board;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jp.games_ranc.DTO.board.PostLikeResponse;
import jp.games_ranc.service.board.PostLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "PostLike", description = "게시글 좋아요 API")
@RestController
@RequestMapping("/api/posts/{postId}/likes")
@RequiredArgsConstructor
public class PostLikeController {

    private final PostLikeService postLikeService;

    @Operation(summary = "좋아요 추가", description = "게시글에 좋아요를 추가합니다.")
    @PostMapping
    public ResponseEntity<PostLikeResponse> addLike(
            @PathVariable Long postId,
            @RequestParam Long userId) {
        return ResponseEntity.ok(postLikeService.addLike(postId, userId));
    }

    @Operation(summary = "좋아요 취소", description = "게시글의 좋아요를 취소합니다.")
    @DeleteMapping
    public ResponseEntity<Void> removeLike(
            @PathVariable Long postId,
            @RequestParam Long userId) {
        postLikeService.removeLike(postId, userId);
        return ResponseEntity.ok().build();
    }
}
