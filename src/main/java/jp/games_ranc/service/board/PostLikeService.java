package jp.games_ranc.service.board;

import jp.games_ranc.DTO.board.PostLikeResponse;
import jp.games_ranc.entity.board.Post;
import jp.games_ranc.entity.board.PostLike;
import jp.games_ranc.entity.User;
import jp.games_ranc.repository.board.PostLikeRepository;
import jp.games_ranc.repository.board.PostRepository;
import jp.games_ranc.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostLikeService {

    private final PostLikeRepository postLikeRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Transactional
    public PostLikeResponse addLike(Long postId, Long userId) {
        // 이미 좋아요를 눌렀는지 확인
        if (postLikeRepository.existsByPostIdAndUserId(postId, userId)) {
            throw new IllegalStateException("이미 좋아요를 누른 게시글입니다.");
        }

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        PostLike postLike = PostLike.builder()
                .post(post)
                .user(user)
                .build();

        postLikeRepository.save(postLike);
        post.incrementLikeCount();

        return new PostLikeResponse(post.getLikeCount());
    }

    @Transactional
    public void removeLike(Long postId, Long userId) {
        PostLike postLike = postLikeRepository.findByPostIdAndUserId(postId, userId)
                .orElseThrow(() -> new IllegalArgumentException("좋아요를 찾을 수 없습니다."));

        Post post = postLike.getPost();
        post.decrementLikeCount();

        postLikeRepository.delete(postLike);
    }
}
