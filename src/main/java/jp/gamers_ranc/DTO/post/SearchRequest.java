package jp.gamers_ranc.DTO.post;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class SearchRequest {
    private String keyword;
    private String category;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String author;
}