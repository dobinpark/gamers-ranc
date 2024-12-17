package jp.gamers_ranc.exception;

public class PostNotFoundException extends BusinessException {

    public PostNotFoundException(Long id) {
        super("게시글을 찾을 수 없습니다: " + id);
    }
}
