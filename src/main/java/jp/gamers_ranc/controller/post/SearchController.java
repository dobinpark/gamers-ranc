package jp.gamers_ranc.controller.post;

import jp.gamers_ranc.DTO.post.PageResponse;
import jp.gamers_ranc.DTO.post.PostResponse;
import jp.gamers_ranc.DTO.post.SearchRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/search")
public class SearchController {
    @GetMapping
    public ResponseEntity<PageResponse<PostResponse>> search(
            SearchRequest request,
            Pageable pageable) {
        return ResponseEntity.ok(searchService.search(request, pageable));
    }
}