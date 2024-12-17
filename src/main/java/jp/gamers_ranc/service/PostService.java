package jp.gamers_ranc.service;

import jp.gamers_ranc.DTO.post.PostCreateRequest;
import jp.gamers_ranc.DTO.post.PostResponse;
import jp.gamers_ranc.DTO.post.PostUpdateRequest;
import jp.gamers_ranc.Entity.post.Post;
import jp.gamers_ranc.Entity.user.PointHistory;
import jp.gamers_ranc.Entity.user.PointRule;
import jp.gamers_ranc.Entity.user.User;
import jp.gamers_ranc.exception.PostNotFoundException;
import jp.gamers_ranc.exception.UnauthorizedException;
import jp.gamers_ranc.exception.UserNotFoundException;
import jp.gamers_ranc.repository.PointHistoryRepository;
import jp.gamers_ranc.repository.PostRepository;
import jp.gamers_ranc.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostService <T extends Post> {

    private final PostRepository<T> postRepository;
    private final UserService userService;
    private final UserRepository userRepository;
    private final PointHistoryRepository pointHistoryRepository;

    // 게시글 생성
    @Transactional
    public PostResponse createPost(String email, PostCreateRequest request,
                                   BiFunction<PostCreateRequest, User, T> creator) {

        User user = userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException(email));

        T post = creator.apply(request, user);
        T savedPost = postRepository.save(post);

        // 포인트 적립
        user.addPoint(PointRule.POST_CREATION.getPoints());
        pointHistoryRepository.save(PointHistory.builder()
                .user(user)
                .pointAmount(PointRule.POST_CREATION.getPoints())
                .description(PointRule.POST_CREATION.getDescription())
                .build());

        return PostResponse.from(postRepository.save(post));
    }

    // 게시글 조회
    @Transactional
    public PostResponse getPost(Long id) {
        T post = postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException(id));
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
                .orElseThrow(() -> new UnauthorizedException("수정 권한이 없습니다"));

        post.update(request.getTitle(), request.getContent());
        return PostResponse.from(post);
    }

    // 게시글 삭제
    @Transactional
    public void deletePost(Long id, String userEmail) {
        User user = userService.findUserByEmail(userEmail);
        T post = postRepository.findByIdAndAuthor(id, user)
                .orElseThrow(() -> new UnauthorizedException("삭제 권한이 없습니다"));

        postRepository.delete(post);
    }

    @Transactional
    public void deletePostByAdmin(Long postId, String adminEmail) {
        User admin = userRepository.findByEmail(adminEmail)
                .orElseThrow(() -> new UserNotFoundException(adminEmail));

        if (!admin.isAdmin()) {
            throw new UnauthorizedException("관리자 권한이 필요합니다.");
        }

        T post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException(postId));

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
                .orElseThrow(() -> new PostNotFoundException(id));
    }
}
