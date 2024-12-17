package jp.gamers_ranc.Entity.user;

import jakarta.persistence.*;
import jp.gamers_ranc.Entity.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PointHistory extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private Long pointAmount;

    private String description;

    @Builder
    public PointHistory(User user, Long pointAmount, String description) {
        this.user = user;
        this.pointAmount = pointAmount;
        this.description = description;
    }
}
