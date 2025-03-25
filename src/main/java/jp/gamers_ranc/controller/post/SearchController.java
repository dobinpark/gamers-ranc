package jp.gamers_ranc.controller.post;

import jp.gamers_ranc.DTO.post.PageResponse;
import jp.gamers_ranc.DTO.post.PostResponse;
import jp.gamers_ranc.DTO.post.SearchRequest;
import jp.gamers_ranc.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/search")
public class SearchController {

private final SearchService searchService;

    @Autowired
    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @GetMapping
    public ResponseEntity<PageResponse<PostResponse>> searchPosts(@ModelAttribute SearchRequest request, Pageable pageable) {
        Page<PostResponse> postPage = searchService.search(request, pageable);
        PageResponse<PostResponse> pageResponse = new PageResponse<>(postPage);
        return ResponseEntity.ok(pageResponse);
    }
}
