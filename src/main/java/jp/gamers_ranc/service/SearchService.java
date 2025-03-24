package jp.gamers_ranc.service;

import jp.gamers_ranc.DTO.post.PostResponse;
import jp.gamers_ranc.DTO.post.SearchRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import jp.gamers_ranc.Entity.post.Post;
import jp.gamers_ranc.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class SearchService {

    private final PostRepository<? extends Post> postRepository;

    @Autowired
    public SearchService(PostRepository<? extends Post> postRepository) {
        this.postRepository = postRepository;
    }

    public Page<PostResponse> search(SearchRequest request, Pageable pageable) {
        String keyword = request.getKeyword();
        if (keyword == null || keyword.trim().isEmpty()) {
            return Page.empty();
        }

        Page<? extends Post> postPage = postRepository.findByTitleContainingOrContentContaining(keyword, keyword, pageable);

        return postPage.map(PostResponse::from);
    }
}
