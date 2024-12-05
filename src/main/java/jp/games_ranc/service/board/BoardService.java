package jp.games_ranc.service.board;

import jp.games_ranc.DTO.board.PostRequest;
import jp.games_ranc.DTO.board.PostResponse;
import jp.games_ranc.entity.board.Board;
import jp.games_ranc.entity.board.Post;
import jp.games_ranc.repository.board.BoardRepository;
import jp.games_ranc.repository.board.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final PostRepository postRepository;

    @Transactional
    public PostResponse createPost(String boardCode, PostRequest request) {
        Board board = boardRepository.findByBoardCode(boardCode)
                .orElseThrow(() -> new IllegalArgumentException("게시판을 찾을 수 없습니다."));

        Post post = Post.builder()
                .board(board)
                .title(request.getTitle())
                .content(request.getContent())
                .build();

        Post savedPost = postRepository.save(post);
        return new PostResponse(savedPost);
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getPosts(String boardCode, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return postRepository.findByBoard_BoardCode(boardCode, pageRequest)
                .getContent()
                .stream()
                .map(PostResponse::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public PostResponse getPost(String boardCode, Long postId) {
        Post post = postRepository.findByIdAndBoard_BoardCode(postId, boardCode)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));
        return new PostResponse(post);
    }

    @Transactional
    public PostResponse updatePost(String boardCode, Long postId, PostRequest request) {
        Post post = postRepository.findByIdAndBoard_BoardCode(postId, boardCode)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));
        
        post.update(request.getTitle(), request.getContent());
        return new PostResponse(post);
    }

    @Transactional
    public void deletePost(String boardCode, Long postId) {
        Post post = postRepository.findByIdAndBoard_BoardCode(postId, boardCode)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));
        
        postRepository.delete(post);
    }

    @Transactional
    public void likePost(String boardCode, Long postId) {
        Post post = postRepository.findByIdAndBoard_BoardCode(postId, boardCode)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));
        
        post.incrementLikeCount();
    }

    @Transactional
    public void pointRecommendPost(String boardCode, Long postId, int points) {
        Post post = postRepository.findByIdAndBoard_BoardCode(postId, boardCode)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));
        
        post.addPointCount(points);
    }
}
