package jp.games_ranc.entity.board;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String boardCode;

    private String name;
    private String description;

    @Builder
    public Board(String boardCode, String name, String description) {
        this.boardCode = boardCode;
        this.name = name;
        this.description = description;
    }
}
