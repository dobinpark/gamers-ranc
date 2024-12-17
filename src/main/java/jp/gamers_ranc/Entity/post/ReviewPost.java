package jp.gamers_ranc.Entity.post;

import jakarta.persistence.*;
import jp.gamers_ranc.Entity.user.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
