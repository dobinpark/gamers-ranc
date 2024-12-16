package jp.games_ranc.Entity.post;

import jakarta.persistence.*;
import jp.games_ranc.Entity.user.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Entity
@Getter
@DiscriminatorValue("Review")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReviewPost extends Post {

    @Builder
    public ReviewPost(String title, String content, User author) {
        super(title, content, author);
    }
}
