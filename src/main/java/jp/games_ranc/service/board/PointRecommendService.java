package jp.games_ranc.service.board;

import jp.games_ranc.DTO.board.PointRecommendResponse;
import jp.games_ranc.entity.board.Post;
import jp.games_ranc.entity.board.PointRecommend;
import jp.games_ranc.entity.User;
import jp.games_ranc.repository.board.PointRecommendRepository;
import jp.games_ranc.repository.board.PostRepository;
import jp.games_ranc.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PointRecommendService {

    private final PointRecommendRepository pointRecommendRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Transactional
    public PointRecommendResponse addPointRecommend(Long postId, Long userId, int pointAmount) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        // 사용자의 포인트가 충분한지 확인
        if (user.getPoints() < pointAmount) {
            throw new IllegalStateException("포인트가 부족합니다.");
        }

        // 포인트 차감
        user.usePoints(pointAmount);

        PointRecommend pointRecommend = PointRecommend.builder()
                .post(post)
                .user(user)
                .pointAmount(pointAmount)
                .build();

        pointRecommendRepository.save(pointRecommend);
        post.addPointCount(pointAmount);

        return new PointRecommendResponse(post.getPointCount());
    }

    @Transactional(readOnly = true)
    public PointRecommendResponse getPointRecommends(Long postId) {
        int totalPoints = pointRecommendRepository.sumPointAmountByPostId(postId);
        return new PointRecommendResponse(totalPoints);
    }
}
