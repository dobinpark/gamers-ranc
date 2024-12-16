package jp.games_ranc.controller.post;

import jakarta.validation.Valid;
import jp.games_ranc.DTO.post.PostCreateRequest;
import jp.games_ranc.DTO.post.PostResponse;
import jp.games_ranc.DTO.post.PostUpdateRequest;
import jp.games_ranc.Entity.post.FreePost;
import jp.games_ranc.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/freePost")
@RequiredArgsConstructor
public class FreeController {

    private final PostService<FreePost> postService;

    // 게시글 생성
    @PostMapping
    public ResponseEntity<PostResponse> createPost(
            @RequestHeader("X-USER-EMAIL") String userEmail,
            @Valid @RequestBody PostCreateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(postService.createPost(userEmail, request,
                        req -> FreePost.builder()
                                .title(req.getTitle())
                                .content(req.getContent())
                                .build()));
    }

    // 게시글 조회
    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> getPost(@PathVariable Long id) {
        PostResponse response = postService.getPost(id);
        return ResponseEntity.ok(response);
    }

    // 게시글 목록 조회
    @GetMapping
    public ResponseEntity<List<PostResponse>> getAllPosts() {
        List<PostResponse> response = postService.getAllPosts();
        return ResponseEntity.ok(response);
    }

    // 게시글 수정
    @PutMapping("/{id}")
    public ResponseEntity<PostResponse> updatePost(
            @PathVariable Long id,
            @RequestHeader("X-USER-EMAIL") String userEmail,
            @Valid @RequestBody PostUpdateRequest request) {
        PostResponse response = postService.updatePost(id, userEmail, request);
        return ResponseEntity.ok(response);
    }

    // 게시글 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(
            @PathVariable Long id,
            @RequestHeader("X-USER-EMAIL") String userEmail) {
        postService.deletePost(id, userEmail);
        return ResponseEntity.noContent().build();
    }
}
