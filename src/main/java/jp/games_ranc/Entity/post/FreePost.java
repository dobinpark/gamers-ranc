package jp.games_ranc.Entity.post;

import jakarta.persistence.*;
import jp.games_ranc.Entity.user.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@DiscriminatorValue("Free")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FreePost extends Post {

    @Builder
    public FreePost(String title, String content, User author) {
        super(title, content, author);
    }
}
