package jp.games_ranc.controller.board;

import jp.games_ranc.DTO.board.PostRequest;
import jp.games_ranc.DTO.board.PostResponse;
import jp.games_ranc.service.board.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/boards")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @PostMapping("/{boardCode}/posts")
    public ResponseEntity<PostResponse> createPost(@PathVariable String boardCode, @RequestBody PostRequest request) {
        PostResponse postResponse = boardService.createPost(boardCode, request);
        return ResponseEntity.ok(postResponse);
    }

    @GetMapping("/{boardCode}/posts/{postId}")
    public ResponseEntity<PostResponse> getPost(@PathVariable String boardCode, @PathVariable Long postId) {
        PostResponse postResponse = boardService.getPost(boardCode, postId);
        return ResponseEntity.ok(postResponse);
    }

    @GetMapping("/{boardCode}/posts")
    public ResponseEntity<List<PostResponse>> getPosts(@PathVariable String boardCode, @RequestParam int page,
                                                       @RequestParam int size) {
        List<PostResponse> posts = boardService.getPosts(boardCode, page, size);
        return ResponseEntity.ok(posts);
    }

    @PutMapping("/{boardCode}/posts/{postId}")
    public ResponseEntity<PostResponse> updatePost(@PathVariable String boardCode, @PathVariable Long postId,
                                                   @RequestBody PostRequest request) {
        PostResponse postResponse = boardService.updatePost(boardCode, postId, request);
        return ResponseEntity.ok(postResponse);
    }

    @DeleteMapping("/{boardCode}/posts/{postId}")
    public ResponseEntity<Void> deletePost(@PathVariable String boardCode, @PathVariable Long postId) {
        boardService.deletePost(boardCode, postId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{boardCode}/posts/{postId}/like")
    public ResponseEntity<Void> likePost(@PathVariable String boardCode, @PathVariable Long postId) {
        boardService.likePost(boardCode, postId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{boardCode}/posts/{postId}/point-recommend")
    public ResponseEntity<Void> pointRecommendPost(@PathVariable String boardCode, @PathVariable Long postId,
                                                   @RequestParam int points) {
        boardService.pointRecommendPost(boardCode, postId, points);
        return ResponseEntity.ok().build();
    }
}
