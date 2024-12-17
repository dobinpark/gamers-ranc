package jp.gamers_ranc.controller.post;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jp.gamers_ranc.DTO.post.PostCreateRequest;
import jp.gamers_ranc.DTO.post.PostResponse;
import jp.gamers_ranc.DTO.post.PostUpdateRequest;
import jp.gamers_ranc.Entity.post.ReviewPost;
import jp.gamers_ranc.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviewPost")
@RequiredArgsConstructor
@Tag(name = "Review Board", description = "리뷰 게시판 API")
public class ReviewController {

    private final PostService<ReviewPost> postService;

    // 게시글 생성
    @Operation(summary = "게시글 작성", description = "리뷰 게시판에 새 게시글을 작성합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "게시글 작성 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청")
    })
    @PostMapping
    public ResponseEntity<PostResponse> createPost(
            @RequestHeader("X-USER-EMAIL") String userEmail,
            @Valid @RequestBody PostCreateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(postService.createPost(userEmail, request,
                        (req, user) -> ReviewPost.builder()
                                .title(req.getTitle())
                                .content(req.getContent())
                                .author(user)
                                .build()));
    }

    // 게시글 조회
    @Operation(summary = "게시글 조회", description = "특정 게시글을 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(responseCode = "404", description = "게시글 없음")
    })
    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> getPost(@PathVariable Long id) {
        PostResponse response = postService.getPost(id);
        return ResponseEntity.ok(response);
    }

    // 게시글 목록 조회
    @Operation(summary = "게시글 목록 조회", description = "리뷰 게시판의 모든 게시글을 조회합니다.")
    @ApiResponse(responseCode = "200", description = "조회 성공")
    @GetMapping
    public ResponseEntity<List<PostResponse>> getAllPosts() {
        List<PostResponse> response = postService.getAllPosts();
        return ResponseEntity.ok(response);
    }

    // 게시글 수정
    @Operation(summary = "게시글 수정", description = "특정 게시글을 수정합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "수정 성공"),
            @ApiResponse(responseCode = "403", description = "수정 권한 없음"),
            @ApiResponse(responseCode = "404", description = "게시글 없음")
    })
    @PutMapping("/{id}")
    public ResponseEntity<PostResponse> updatePost(
            @PathVariable Long id,
            @RequestHeader("X-USER-EMAIL") String userEmail,
            @Valid @RequestBody PostUpdateRequest request) {
        PostResponse response = postService.updatePost(id, userEmail, request);
        return ResponseEntity.ok(response);
    }

    // 게시글 삭제
    @Operation(summary = "게시글 삭제", description = "특정 게시글을 삭제합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "삭제 성공"),
            @ApiResponse(responseCode = "403", description = "삭제 권한 없음"),
            @ApiResponse(responseCode = "404", description = "게시글 없음")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(
            @PathVariable Long id,
            @RequestHeader("X-USER-EMAIL") String userEmail) {
        postService.deletePost(id, userEmail);
        return ResponseEntity.noContent().build();
    }

    // 관리자 권한으로 게시글 삭제
    @Operation(summary = "관리자 권한으로 게시글 삭제", description = "관리자가 특정 게시글을 강제 삭제합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "삭제 성공"),
            @ApiResponse(responseCode = "403", description = "관리자 권한 없음"),
            @ApiResponse(responseCode = "404", description = "게시글 없음")
    })
    @DeleteMapping("/admin/{id}")
    public ResponseEntity<Void> deletePostByAdmin(
            @PathVariable Long id,
            @RequestHeader("X-USER-EMAIL") String adminEmail) {
        postService.deletePostByAdmin(id, adminEmail);
        return ResponseEntity.noContent().build();
    }
}
