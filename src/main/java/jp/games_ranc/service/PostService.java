package jp.games_ranc.service;

import jp.games_ranc.DTO.post.PostCreateRequest;
import jp.games_ranc.DTO.post.PostResponse;
import jp.games_ranc.DTO.post.PostUpdateRequest;
import jp.games_ranc.Entity.post.Post;
import jp.games_ranc.Entity.user.User;
import jp.games_ranc.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostService <T extends Post> {

    private final PostRepository<T> postRepository;
    private final UserService userService;

    // 게시글 생성
    @Transactional
    public PostResponse createPost(String userEmail, PostCreateRequest request, BiFunction<PostCreateRequest, User, T> creator) {
        User user = userService.findUserByEmail(userEmail);
        T post = creator.apply(request, user);
        return PostResponse.from(postRepository.save(post));
    }

    // 게시글 조회
    @Transactional
    public PostResponse getPost(Long id) {
        T post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다"));
        post.increaseViewCount();
        return PostResponse.from(post);
    }

    // 게시글 목록 조회
    public List<PostResponse> getAllPosts() {
return postRepository.findAllByOrderByCreatedAtDesc().stream()
                .map(PostResponse::from)
                .collect(Collectors.toList());
    }

    // 페이징 처리된 게시글 목록 조회
    public Page<PostResponse> getPagedPosts(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        return postRepository.findAllByOrderByCreatedAtDesc(pageable)
                .map(PostResponse::from);
    }

    // 게시글 수정
    @Transactional
    public PostResponse updatePost(Long id, String userEmail, PostUpdateRequest request) {
        User user = userService.findUserByEmail(userEmail);
        T post = postRepository.findByIdAndAuthor(id, user)
                .orElseThrow(() -> new IllegalArgumentException("수정 권한이 없습니다"));

        post.update(request.getTitle(), request.getContent());
        return PostResponse.from(post);
    }

    // 게시글 삭제
    @Transactional
    public void deletePost(Long id, String userEmail) {
        User user = userService.findUserByEmail(userEmail);
        T post = postRepository.findByIdAndAuthor(id, user)
                .orElseThrow(() -> new IllegalArgumentException("삭제 권한이 없습니다"));

        postRepository.delete(post);
    }

    public Page<PostResponse> searchPosts(String keyword, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return postRepository.findByTitleContainingOrContentContaining(
                        keyword, keyword, pageable)
                .map(PostResponse::from);
    }

    public T getPostEntity(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다"));
    }
}
