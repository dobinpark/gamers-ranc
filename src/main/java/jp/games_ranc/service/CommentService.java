package jp.games_ranc.service;

import jp.games_ranc.DTO.post.CommentCreateRequest;
import jp.games_ranc.DTO.post.CommentResponse;
import jp.games_ranc.Entity.post.Comment;
import jp.games_ranc.Entity.post.Post;
import jp.games_ranc.Entity.user.User;
import jp.games_ranc.repository.post.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostService<Post> postService;
    private final UserService userService;

    @Transactional
    public CommentResponse createComment(Long postId, String userEmail, CommentCreateRequest request) {
        User user = userService.findUserByEmail(userEmail);
        Post post = postService.getPostEntity(postId);

        Comment comment = Comment.builder()
                .content(request.getContent())
                .post(post)
                .author(user)
                .build();

        return CommentResponse.from(commentRepository.save(comment));
    }
}
