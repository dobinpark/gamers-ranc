package jp.games_ranc.service.board;

import jp.games_ranc.DTO.board.CommentRequest;
import jp.games_ranc.DTO.board.CommentResponse;
import jp.games_ranc.entity.User;
import jp.games_ranc.entity.board.Comment;
import jp.games_ranc.entity.board.Post;
import jp.games_ranc.repository.UserRepository;
import jp.games_ranc.repository.board.CommentRepository;
import jp.games_ranc.repository.board.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Transactional
    public CommentResponse createComment(Long postId, Long userId, String content, Long parentId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        Comment parent = null;
        if (parentId != null) {
            parent = commentRepository.findById(parentId)
                    .orElseThrow(() -> new IllegalArgumentException("부모 댓글을 찾을 수 없습니다."));
        }

        Comment comment = Comment.builder()
                .post(post)
                .author(user)
                .parent(parent)
                .content(content)
                .build();

        commentRepository.save(comment);

        return toCommentResponse(comment);
    }

    @Transactional
    public void deleteComment(Long commentId, Long userId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("댓글을 찾을 수 없습니다."));

        if (!comment.getAuthor().getId().equals(userId)) {
            throw new IllegalStateException("이 댓글을 삭제할 권한이 없습니다.");
        }

        commentRepository.delete(comment);
    }

    @Transactional(readOnly = true)
    public Page<CommentResponse> getComments(Long postId, Pageable pageable) {
        Page<Comment> comments = commentRepository.findByPostIdAndParentIsNull(postId, pageable);
        return comments.map(this::toCommentResponse);
    }

    private CommentResponse toCommentResponse(Comment comment) {
        return CommentResponse.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .authorId(comment.getAuthor().getId())
                .authorName(comment.getAuthor().getNickname())
                .parentId(comment.getParent() != null ? comment.getParent().getId() : null)
                .createdAt(comment.getCreatedAt())
                .modifiedAt(comment.getUpdatedAt())
                .build();
    }
}