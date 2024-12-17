package jp.gamers_ranc.DTO.post;

import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentCreateRequest {

    @NotBlank(message = "내용은 필수입니다")
    private String content;
}
